package com.cricketService.dto.series;

import com.cricketService.enums.CricketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesCreateDto {

    private CricketType type;

}
