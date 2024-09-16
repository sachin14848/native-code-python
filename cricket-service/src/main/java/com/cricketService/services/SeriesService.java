package com.cricketService.services;

import com.cricketService.dto.SeriesDto;
import com.cricketService.dto.SeriesRapidDto;
import com.cricketService.entities.Series;
import com.cricketService.entities.SeriesWithDate;
import com.cricketService.repo.SeriesRepository;
import com.cricketService.repo.SeriesWithDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesWithDateRepository seriesWithDateRepository;
    private final RestTemplate restTemplate;

    @Value("${rapid.baseURL}")
    private String baseUrl;
    @Value("${rapid.urls.series}")
    private String seriesUrl;

    public SeriesRapidDto createSeriesList() {
        // fetch data from Rapid API
        final String url = baseUrl + seriesUrl;
        SeriesRapidDto seriesList = null;
        try {
            seriesList = restTemplate.getForObject(url, SeriesRapidDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert seriesList != null;
        List<SeriesWithDate> date = seriesList.getSeriesMapProto().stream().map(SeriesWithDate::new).toList();
        seriesWithDateRepository.saveAll(date);
        return seriesList;
    }

    public List<SeriesWithDate> getSeriesWithDateRepository() {
        return seriesWithDateRepository.findAll();
    }

    public SeriesWithDate getSeriesWithMonthID(Long id) {
        return seriesWithDateRepository.findById(id).orElseThrow(() -> new RuntimeException("No such series"));
    }

    public SeriesWithDate getSeriesWithDate(String date) {
        return seriesWithDateRepository.findByDate(date).orElseThrow(() -> new RuntimeException("No such series"));
    }

    public SeriesDto getSingleSeriesById(Long seriesId) {
        return seriesRepository.findById(seriesId).map(SeriesDto::new).orElseThrow(() -> new RuntimeException("No such series"));
    }

    public Series getSingleSeriesSById(int seriesId) {
        return seriesRepository.findById((long) seriesId).orElseThrow(() -> new RuntimeException("No such series"));
    }

    public List<SeriesDto> getAllSeries() {
        return seriesRepository.findAll().stream().map(SeriesDto::new).toList();
    }

}
