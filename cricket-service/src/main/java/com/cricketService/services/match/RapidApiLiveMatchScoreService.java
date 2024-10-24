package com.cricketService.services.match;

import com.cricketService.dto.scoreBoard.RapidScoreCardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RapidApiLiveMatchScoreService {

    private final SimpMessagingTemplate messagingTemplate;

    public RapidApiLiveMatchScoreService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Fetch and push the live cricket score from the third-party API
    public void fetchAndPushScoreForMatch(String matchId) {
        RapidScoreCardDto score = fetchScoreFromApi(matchId);  // Call third-party API

        // Push the score update to WebSocket subscribers
        messagingTemplate.convertAndSend("/topic/match/" + matchId, score);
    }

    // Method to call third-party API (mock implementation)
    private RapidScoreCardDto fetchScoreFromApi(String matchId) {
        // Call third-party API logic here...
        return new RapidScoreCardDto("100", "2");  // Mock response
    }

//    private final RestTemplate restTemplate;
//    private final LiveMatchScoreRedisService redisService;
//
//    @Lazy
//    private final ConnectionManager connectionManager;
//
//
//    public void fetchAndPushScoreForMatch(String matchId) {
//        try {
//            // Check if score is cached
//            RapidScoreCardDto cachedScore = redisService.getScoreFromCache(matchId);
//            if (cachedScore != null) {
//                connectionManager.sendScoreUpdate(matchId, cachedScore); // Push cached score to clients
//                return; // Return early if cached
//            }
//
////            // If not cached, fetch from the external API
////            String apiUrl = "https://api.example.com/cricket/live-score/" + matchId;
////            CricketScore score = restTemplate.getForObject(apiUrl, CricketScore.class);
//            RapidScoreCardDto score = new RapidScoreCardDto("Team 1", "Team 2");  // Replace with actual API call
//
//            if (score != null) {
//                redisService.saveScoreToCache(matchId, score);
//                connectionManager.sendScoreUpdate(matchId, score);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
