package com.question_generator.rabbitMqService;

import com.question_generator.rabbitMqService.rabbitMqDto.ScoreMessageDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class LiveScoreConsumer {
    @RabbitListener(queues = "liveSScore")
    public void receiveScoreUpdate(ScoreMessageDto scoreMessageDto) {
        System.out.println("Received message from queue: " + scoreMessageDto);
        // Process the score message here
    }
}
