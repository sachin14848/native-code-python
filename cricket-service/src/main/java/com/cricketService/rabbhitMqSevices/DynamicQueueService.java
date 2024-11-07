package com.cricketService.rabbhitMqSevices;

import com.cricketService.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DynamicQueueService {


    private final RabbitAdmin rabbitAdmin;
    private final DirectExchange exchange;
//    private final RabbitMQConfig rabbitMQConfig;

//    public DynamicQueueService(RabbitAdmin rabbitAdmin, DirectExchange exchange, RabbitMQConfig rabbitMQConfig) {
//        this.rabbitAdmin = rabbitAdmin;
//        this.exchange = exchange;
//        this.rabbitMQConfig = rabbitMQConfig;
//    }
//
//    public void createQueueAndBinding(String matchId) {
//        Queue queue = rabbitMQConfig.createQueue(matchId);
//        Binding binding = rabbitMQConfig.createBinding(matchId, exchange);
//
//        rabbitAdmin.declareQueue(queue);
//        rabbitAdmin.declareBinding(binding);
//
//        System.out.println("Queue and Binding created for matchId: " + matchId);
//    }

    public Queue createQueue(String matchId) {
        Queue queue = new Queue("livescore." + matchId, false);
        rabbitAdmin.declareQueue(queue); // Register queue with RabbitMQ
        return queue;
    }

    public Binding createBinding(String matchId) {
        Queue queue = createQueue(matchId);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("livescore." + matchId);
        rabbitAdmin.declareBinding(binding); // Register binding with RabbitMQ
        return binding;
    }

}
