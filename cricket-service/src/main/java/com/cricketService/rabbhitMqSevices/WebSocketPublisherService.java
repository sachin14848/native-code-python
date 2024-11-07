package com.cricketService.rabbhitMqSevices;

import com.cricketService.rabbhitMqSevices.rabbitMqDto.ScoreMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public WebSocketPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendScoreUpdate(ScoreMessageDto scoreMessageDto) {
        rabbitTemplate.convertAndSend("cricket-score-exchange", "livescore", scoreMessageDto);
//        rabbitTemplate.convertAndSend("", "liveSScore", scoreMessageDto);
        System.out.println("Sent message to queue: " + scoreMessageDto);
    }

//    public void sendScoreUpdate(String matchId, Object scoreUpdate) {
//        String routingKey = "livescore." + matchId;
//        ScoreMessageDto message = new ScoreMessageDto(matchId, scoreUpdate);
//        rabbitTemplate.convertAndSend("livescore.exchange", routingKey, message);
//        System.out.println("Score update sent for matchId: " + matchId);
//    }

}
