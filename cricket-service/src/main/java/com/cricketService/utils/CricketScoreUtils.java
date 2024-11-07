package com.cricketService.utils;

import com.cricketService.dto.ScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CricketScoreUtils {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendScoreCardUpdate(ScoreDto scoreCard) {
        messagingTemplate.convertAndSend("/topic/live-score/" + scoreCard.getScoreCard().getMatchHeader().getMatchId(), scoreCard);
    }


}