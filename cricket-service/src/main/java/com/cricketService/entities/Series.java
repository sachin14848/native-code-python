package com.cricketService.entities;

import com.cricketService.dto.SeriesDto;
import com.cricketService.entities.match.MatchInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "series")
public class Series {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series_name")
    private String name;

    @Column(name = "start_date")
    private String startDt;

    @Column(name = "end_date")
    private String endDt;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private SeriesWithDate seriesWithDate;

    public Series(SeriesDto seriesDto) {
        this.id = seriesDto.getId();
        this.name = seriesDto.getName();
        this.startDt = seriesDto.getStartDt();
        this.endDt = seriesDto.getEndDt();
    }

}
