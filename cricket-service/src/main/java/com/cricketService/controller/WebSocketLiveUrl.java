package com.cricketService.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Slf4j
@Controller
public class WebSocketLiveUrl {

//    private final SimpMessagingTemplate messagingTemplate;
//
//    //    @GetMapping("/live-score/{matchId}")
//    @MessageMapping("/live-score")
////    @SendTo("/topic/live-score")
//    @SubscribeMapping("/live-score")
//    public void updateScore() {
//        log.info("Received score update for match:");
////        log.info("Score update: {}", scoreUpdate);
////
////        // Broadcast the score update to all clients subscribed to /topic/live-score/{matchId}
////        messagingTemplate.convertAndSend("/topic/live-score/" + matchId, scoreUpdate);
//    }

}
