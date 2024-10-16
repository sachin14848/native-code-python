package com.cricketService.controller;

import com.cricketService.services.match.UpcomingMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cricket/upcomingMatches")
public class UpcomingMatchController {
    private final UpcomingMatchService upcomingMatchService;

//    @RequestMapping
//    public ResponseEntity<> fetchUpcomingMatches() {
//        return upcomingMatchService.fetchUpcomingMatches().toString();
//    }
}
