package com.mailSending.config;

import com.mailSending.passwordEncoder.BCryptPasswordEncoder;
import com.mailSending.passwordEncoder.PasswordEncoder;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routingkey.name}")
    private String routingKey;


    @Bean
    public Queue mainQueue() {
        Map<String, Object> args = new HashMap<>();
        // Configure the queue to send failed messages to a DLX
        args.put("x-dead-letter-exchange", "dlx_exchange");
        args.put("x-dead-letter-routing-key", "dlx_routing_key");
        return new Queue("main_queue", true, false, false, args);
    }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("dlx_exchange");
    }

    @Bean
    public Queue dlxQueue() {
        return new Queue("dlx_queue");
    }

    @Bean
    public Binding dlxBinding(DirectExchange dlxExchange, Queue dlxQueue) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("dlx_routing_key");
    }


    @Bean // it is used to handle response from consumer to producer
    public Queue replyQueue() {
        return new Queue("reply_queue", false);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }


    // Email Exchange
    @Bean
    public Exchange emailExchange() {
        return new DirectExchange("email_exchange");
    }

    // Primary Email Queue with Dead-lettering enabled
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable("email_queue")
                .deadLetterExchange("dlx_exchange")   // Configure DLX
                .deadLetterRoutingKey("dead_letter")  // Configure DLK
                .build();
    }

    // Dead-letter Exchange (DLX)
    @Bean
    public Exchange deadLetterExchange() {
        return new DirectExchange("dlx_exchange");
    }

    // Dead-letter Queue (DLQ)
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("dlq").build();
    }

    // Bind primary queue to the email exchange
    @Bean
    public Binding emailBinding(Queue emailQueue, Exchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("send_email").noargs();
    }

    // Bind dead-letter queue to the DLX
    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, Exchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("dead_letter").noargs();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new CustomMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RetryTemplate retryTemplate) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new CustomMessageConverter());
        rabbitTemplate.setRetryTemplate(retryTemplate);
        return rabbitTemplate;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Configure the backoff policy (e.g., wait 2 seconds between retries)
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(2000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        // Configure the retry policy (e.g., retry 3 times)
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
