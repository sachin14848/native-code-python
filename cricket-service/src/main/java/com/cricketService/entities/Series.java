package com.cricketService.entities;

import com.cricketService.dto.series.SeriesDto;
import com.cricketService.enums.CricketType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "series")
public class Series {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series_name", unique = true)
    private String name;

    @Column(name = "start_date")
    private String startDt;

    @Column(name = "end_date")
    private String endDt;

    @Column(name = "type", nullable = false)
    private CricketType type;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private SeriesWithDate seriesWithDate;

    public Series(SeriesDto seriesDto, CricketType type) {
        this.id = seriesDto.getId();
        this.name = seriesDto.getName();
        this.startDt = seriesDto.getStartDt();
        this.endDt = seriesDto.getEndDt();
        this.type= type;
    }

}
