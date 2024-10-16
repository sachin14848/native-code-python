package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.dto.match.MatchInfoDto;
import com.cricketService.dto.match.MatchRapidApi;
import com.cricketService.entities.SeriesWithDate;
import com.cricketService.services.MatchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cricket/matches")
public class MatchController {

    private static final Logger log = LoggerFactory.getLogger(MatchController.class);
    private final MatchService matchService;

    @GetMapping(value = "/create/matchInfo/{seriesId}", produces = "application/json")
    private ResponseEntity<CommonResponse<MatchRapidApi>> createMatchInfo(@PathVariable String seriesId) {
        CommonResponse<MatchRapidApi> response = new CommonResponse<>();
        MatchRapidApi match = matchService.createMatch(seriesId);
        response.setData(match);
        response.setStatus(true);
        response.setStatusCode(HttpStatus.FOUND.value());
        response.setMessage("Match Created");
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(value = "/matchInfo/{id}", produces = "application/json")
    private ResponseEntity<CommonResponse<List<MatchInfoDto>>> getMatchInfo(@PathVariable int id) {
        CommonResponse<List<MatchInfoDto>> response = new CommonResponse<>();
        try {
            List<MatchInfoDto> matchInfoDto = matchService.getMatchInfoBySeriesId(id);
            if (matchInfoDto == null) {
                throw new RuntimeException("Series not found");
            }
            log.info("MatchInfo  by series id: {}", matchInfoDto);
            response.setData(matchInfoDto);
            response.setStatus(true);
            response.setMessage("Match with Series id found");
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (RuntimeException e) {
            response.setStatus(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/matchInfo/{id}/matchId", produces = "application/json")
    private ResponseEntity<CommonResponse<MatchInfoDto>> getMatchInfoByMatchId(@PathVariable Long id) {
        CommonResponse<MatchInfoDto> response = new CommonResponse<>();
        try {
            MatchInfoDto matchInfoDto = matchService.getMatchInfoByMatchId(id);
            if (matchInfoDto == null) {
                throw new RuntimeException("Series not found");
            }
            response.setData(matchInfoDto);
            response.setStatus(true);
            response.setMessage("Match with Series id founded");
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (RuntimeException e) {
            log.info("Exception occurred while creating new {}", e.getMessage());
            response.setStatus(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
