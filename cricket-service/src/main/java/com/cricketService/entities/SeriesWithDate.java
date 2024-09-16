package com.cricketService.entities;

import com.cricketService.dto.SeriesWithDateDto;
import com.cricketService.dto.match.MatchInfoDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "series_with_date")
public class SeriesWithDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private String date;

    @OneToMany(mappedBy = "seriesWithDate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Series> series;

    public SeriesWithDate(SeriesWithDateDto seriesWithDateDto) {
        this.date = seriesWithDateDto.getDate();
        this.series = seriesWithDateDto.getSeries().stream().map(seriesDto -> {
            Series series = new Series(seriesDto);  // Pass SeriesDto to Series constructor
            series.setSeriesWithDate(this);         // Set the parent reference
            return series;
        }).toList();
    }



}
