package com.cricketService.controller;

import com.cricketService.dto.CommonResponse;
import com.cricketService.dto.SeriesDto;
import com.cricketService.dto.SeriesRapidDto;
import com.cricketService.entities.SeriesWithDate;
import com.cricketService.services.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cricket/series")
public class SeriesController {

    @Autowired
    private final SeriesService seriesService;

    @GetMapping("/create")
    public ResponseEntity<SeriesRapidDto> create() {
        return ResponseEntity.ok(seriesService.createSeriesList());
    }

    @GetMapping("/get-series")
    public ResponseEntity<List<SeriesWithDate>> getSeriesWithDate() {
        return ResponseEntity.ok(seriesService.getSeriesWithDateRepository());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<SeriesWithDate>> getSeriesById(@PathVariable Long id) {
        CommonResponse<SeriesWithDate> response = new CommonResponse<>();
        try {
            SeriesWithDate seriesWithDate = seriesService.getSeriesWithMonthID(id);


            if (seriesWithDate == null) {
                return ResponseEntity.notFound().build();
            }
            response.setData(seriesWithDate);
            response.setStatus(true);
            response.setMessage("Series with date");
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (RuntimeException e) {
            response.setStatus(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<CommonResponse<SeriesWithDate>> getSeriesByDate(@RequestParam String date) {
        CommonResponse<SeriesWithDate> response = new CommonResponse<>();
        try {
            SeriesWithDate seriesWithDate = seriesService.getSeriesWithDate(date);
            if (seriesWithDate == null) {
                throw new RuntimeException("Series not found");
            }
            response.setData(seriesWithDate);
            response.setStatus(true);
            response.setMessage("Series with date");
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (RuntimeException e) {
            response.setStatus(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<CommonResponse<SeriesDto>> getSeriesByIdSingle(@PathVariable Long id) {
        CommonResponse<SeriesDto> response = new CommonResponse<>();
        try {
            SeriesDto series = seriesService.getSingleSeriesById(id);


            if (series == null) {
                throw new RuntimeException("Series not found");
            }
            response.setData(series);
            response.setStatus(true);
            response.setMessage("Series with date");
            response.setStatusCode(HttpStatus.FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (RuntimeException e) {
            response.setStatus(false);
            response.setMessage(e.getMessage());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
