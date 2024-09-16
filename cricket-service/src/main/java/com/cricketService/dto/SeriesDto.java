package com.cricketService.dto;

import com.cricketService.entities.Series;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class SeriesDto {

    private Long id;
    private String name;
    private String startDt;
    private String endDt;
    @JsonIgnore
    private SeriesWithDateDto seriesWithDate;

    public SeriesDto(Series series) {
        this.id = series.getId();
        this.name = series.getName();
        this.startDt = series.getStartDt();
        this.endDt = series.getEndDt();
        this.seriesWithDate = new SeriesWithDateDto(); // Assuming SeriesWithDateDto is already created and mapped properly.
    }
}
