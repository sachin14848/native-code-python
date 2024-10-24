package com.cricketService.services;

import com.cricketService.dto.series.SeriesDto;
import com.cricketService.dto.series.SeriesRapidDto;
import com.cricketService.dto.series.SeriesWithDateDto;
import com.cricketService.entities.Series;
import com.cricketService.entities.SeriesWithDate;
import com.cricketService.enums.CricketType;
import com.cricketService.repo.SeriesRepository;
import com.cricketService.repo.SeriesWithDateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Transactional
    public List<SeriesWithDate> createSeriesList(CricketType type) {
        // fetch data from Rapid API
        final String url = baseUrl + seriesUrl + type.name();
        SeriesRapidDto seriesList = null;
        try {
            seriesList = restTemplate.getForObject(url, SeriesRapidDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        assert seriesList != null;
        List<SeriesWithDate> date = seriesList.getSeriesMapProto().stream().map(
                seriesProto -> {
                    Optional<SeriesWithDate> existingSeriesOpt = seriesWithDateRepository.findByDate(seriesProto.getDate());
                    if (existingSeriesOpt.isPresent()) {
                        SeriesWithDate existingSeries = existingSeriesOpt.get();
                        Set<Long> existingSeriesNames = existingSeries.getSeries().stream()
                                .map(Series::getId)
                                .collect(Collectors.toSet());
                        List<Series> updateSeries = seriesProto.getSeries().stream()
                                .filter(series -> !existingSeriesNames.contains(series.getId()))
                                .map(
                                        seriesDto -> {
                                            Series series = new Series(seriesDto, type);  // Pass SeriesDto to Series constructor
                                            series.setSeriesWithDate(existingSeries);         // Set the parent reference
                                            return series;
                                        }).toList();
                        existingSeries.getSeries().addAll(updateSeries);
                        return existingSeries;
                    } else {
                        return new SeriesWithDate(seriesProto, type);
                    }
                }
        ).toList();
        seriesWithDateRepository.saveAll(date);
        return date;
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

    public Boolean isSeriesSaved(Long seriesId) {
        return seriesRepository.existsById(seriesId);
    }

    public Series getSingleSeriesSById(int seriesId) {
        return seriesRepository.findById((long) seriesId).orElseThrow(() -> new RuntimeException("No such series " + seriesId));
    }

    public List<SeriesDto> getAllSeries() {
        return seriesRepository.findAll().stream().map(SeriesDto::new).toList();
    }

}
