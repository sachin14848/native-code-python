package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.dto.upcoming.RequestUpcomingMatchByMatchTypeDto;
import com.cricketService.dto.upcoming.UpComingMatchDto;
import com.cricketService.entities.schedule.UpcomingMatchList;
import com.cricketService.enums.MatchType;
import com.cricketService.services.match.UpcomingMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cricket/upcomingMatches")
public class UpcomingMatchController {
    private final UpcomingMatchService upcomingMatchService;

    @RequestMapping("/")
    public ResponseEntity<CommonResponse<?>> fetchUpcomingMatches() {
        CommonResponse<List<UpcomingMatchList>> response = new CommonResponse<>();
        try {
            List<UpcomingMatchList> upcomingMatchLists = upcomingMatchService.findUpcomingMatchList();
            if (upcomingMatchLists != null) {
                response.setData(upcomingMatchLists);
                response.setError(null);
                response.setMessage("Fetch upcoming match successfully");
                response.setStatus(true);
            }
        } catch (Exception e) {
            response.setData(null);
            response.setError("Failed to Fetch Match");
            response.setMessage(e.getMessage());
            response.setStatus(false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/match-type")
    public ResponseEntity<CommonResponse<List<UpComingMatchDto>>> fetchUpcomingMatchByMatchType(@RequestBody RequestUpcomingMatchByMatchTypeDto matchType) {
        CommonResponse<List<UpComingMatchDto>> response = new CommonResponse<>();
        try {
            List<UpComingMatchDto> upcomingMatchDto = upcomingMatchService.findUpcomingMatchListByMatchType(matchType.getMatchType());
            if (upcomingMatchDto != null) {
                response.setData(upcomingMatchDto);
                response.setError(null);
                response.setMessage("Fetch upcoming match successfully");
                response.setStatus(true);
            } else {
                response.setData(null);
                response.setError("No upcoming match found for the given match type");
                response.setMessage("No upcoming match found");
                response.setStatus(false);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setData(null);
            response.setError("Failed to Fetch Upcoming Match");
            response.setMessage(e.getMessage());
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
