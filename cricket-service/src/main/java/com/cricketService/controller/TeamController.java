package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.dto.RapidDto;
import com.cricketService.dto.TeamsDto;
import com.cricketService.dto.series.SeriesCreateDto;
import com.cricketService.services.TeamListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cricket")
@RequiredArgsConstructor
public class TeamController {

    private final TeamListService teamListService;

    @PostMapping("/teams")
    public ResponseEntity<CommonResponse<RapidDto>> getTeams(@Valid @RequestBody SeriesCreateDto teamCreateDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                // Handle validation errors
                FieldError fieldError = result.getFieldError();
                CommonResponse<RapidDto> response = new CommonResponse<>();
                response.setData(null);
                response.setMessage("Validation Failed");
                response.setError(fieldError != null ? fieldError.getDefaultMessage() : "Unknown error");
                response.setStatus(false);
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            RapidDto team = teamListService.createTeamList(teamCreateDto.getType());
            if (team == null) {
                throw new BadRequestException("Could not create team");
            }
            CommonResponse<RapidDto> response = new CommonResponse<>();
            response.setData(team);
            response.setMessage("Team created successfully");
            response.setError(null);
            response.setStatus(true);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            FieldError fieldError = result.getFieldError();
            CommonResponse<RapidDto> response = new CommonResponse<>();
            response.setData(null);
            response.setMessage("Bad Request");
            if (fieldError != null) {
                response.setError(fieldError.getField());
            } else {
                response.setError("Unknown error");
            }
            response.setStatus(false);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-teams")
    public ResponseEntity<List<TeamsDto>> getAllTeams() {
        return ResponseEntity.ok(teamListService.getTeams());
    }

}
