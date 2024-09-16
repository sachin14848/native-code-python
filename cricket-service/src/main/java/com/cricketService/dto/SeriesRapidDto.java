package com.cricketService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesRapidDto {

    private List<SeriesWithDateDto> seriesMapProto;
}
