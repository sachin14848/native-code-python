package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.entities.schedule.UpcomingMatchList;
import com.cricketService.services.match.UpcomingMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
