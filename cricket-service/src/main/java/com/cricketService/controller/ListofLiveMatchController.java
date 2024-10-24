package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.dto.match.LiveMatchListDto;
import com.cricketService.services.match.LiveMatchScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cricket/live-matches/list")
public class ListofLiveMatchController {

    private final SimpMessagingTemplate messagingTemplate;
    private final LiveMatchScheduleService liveMatchScheduleService;

    @GetMapping("/")
    public ResponseEntity<CommonResponse<?>> getAllLiveMatches() {
        CommonResponse<List<LiveMatchListDto>> response = new CommonResponse<>();
        try {
            List<LiveMatchListDto> data = liveMatchScheduleService.getLiveMatchList();
            response.setData(data);
            response.setStatus(true);
            response.setMessage("Live matches fetched successfully");
            response.setStatusCode(200);
            response.setError(null);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.setData(null);
            response.setStatus(false);
            response.setMessage("Failed to fetch live matches");
            response.setError(ex.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/topic/live-score/{matchId}")
//    public String getSubscriptionDestination(@PathVariable String matchId) {
////        ValueOperations<String, String> valueOps = redisTemplatelate.opsForValue();
//        System.out.println("Match Id : " + matchId );
//        return "jsdhkjsf";
//    }

}
