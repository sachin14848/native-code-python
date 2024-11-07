package com.cricketService.rabbhitMqSevices;

import com.cricketService.rabbhitMqSevices.rabbitMqDto.ScoreMessageDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ScoreConsumer {

    @RabbitListener(queues = "liveSScore")
    public void receiveScoreUpdate(ScoreMessageDto scoreMessageDto) {
        System.out.println("Received message from queue: " + scoreMessageDto);
        // Process the score message here
    }

//    @RabbitListener(queues = "#{dynamicQueueService.createQueueAndBinding('${matchId}').name}")
//    public void receiveScoreUpdate(@Payload Object scoreUpdate) {
//        System.out.println("Received score update: " + scoreUpdate);
//    }

//    private final DynamicQueueService dynamicQueueService;
//    private final ConnectionFactory connectionFactory;
//
//    public ScoreConsumer(DynamicQueueService dynamicQueueService, ConnectionFactory connectionFactory) {
//        this.dynamicQueueService = dynamicQueueService;
//        this.connectionFactory = connectionFactory;
//    }
//
//    public void listenToMatchQueue(String matchId) {
//        dynamicQueueService.createBinding(matchId);
//
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames("livescore." + matchId);
//        container.setMessageListener(new MessageListenerAdapter(this, "receiveMessage"));
//
//        container.start(); // Start listening to the queue
//    }
//
//    // Method to process received messages
//    public void receiveMessage(String message) {
//        System.out.println("Received message: " + message);
//    }

}
