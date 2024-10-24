package com.cricketService.services.test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ThirdPartyApiService {

//    private final ConnectionManager connectionManager;
    private final RestTemplate restTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentHashMap<String, CricketScore> matchScores = new ConcurrentHashMap<>();

    public ThirdPartyApiService(SimpMessagingTemplate messagingTemplate) {
//        this.connectionManager = connectionManager;
        this.messagingTemplate = messagingTemplate;
        this.restTemplate = new RestTemplate();
    }

    public void fetchAndPushScoreForMatch(String matchId) {
        try {
//            String apiUrl = "https://api.example.com/cricket/live-score/" + matchId;
//            CricketScore score = restTemplate.getForObject(apiUrl, CricketScore.class);
            CricketScore score = new CricketScore("skdjfs");
            // If the score changes, update and push to clients
            if (!score.equals(matchScores.get(matchId))) {
                matchScores.put(matchId, score);  // Update stored score
                sendScoreUpdate(matchId, score);  // Push update to clients
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle exceptions (e.g., log errors)
        }
    }


    public void sendScoreUpdate(String matchId, CricketScore score) {
        messagingTemplate.convertAndSend("/topic/live-score/" + matchId, score);
    }

}
