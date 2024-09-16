package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.dto.RapidApiLiveScore;
import com.cricketService.services.MatchLiveScoreService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cricket/live-score")
public class MatchLiveScoreController {

    private final MatchLiveScoreService matchLiveScoreService;

    @RequestMapping("/{matchId}")
    public ResponseEntity<CommonResponse<RapidApiLiveScore>> getLiveScore(@PathVariable int matchId) {
        CommonResponse<RapidApiLiveScore> response = new CommonResponse<>();
        response.setData(matchLiveScoreService.getMatchLiveScore(matchId));
        response.setStatusCode(HttpServletResponse.SC_OK);
        response.setStatus(true);
        response.setMessage("Live score fetched successfully");
        return ResponseEntity.ok(response);
    }

}
