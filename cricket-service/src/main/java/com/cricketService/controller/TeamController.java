package com.cricketService.controller;

import com.cricketService.dto.RapidDto;
import com.cricketService.dto.TeamsDto;
import com.cricketService.entities.TeamsEntity;
import com.cricketService.services.TeamListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cricket")
@RequiredArgsConstructor
public class TeamController {

    private final TeamListService teamListService;

    @GetMapping("/teams")
    public ResponseEntity<RapidDto> getTeams() {
        return ResponseEntity.ok(teamListService.createTeamList());
    }

    @GetMapping("/get-all-teams")
    public ResponseEntity<List<TeamsDto>> getAllTeams() {
        return ResponseEntity.ok(teamListService.getTeams());
    }

}
