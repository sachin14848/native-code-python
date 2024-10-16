package com.cricketService.dto.matchList.typeMatches.matchType;

import com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.SeriesAdWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesMatches {

    private SeriesAdWrapper seriesAdWrapper;

}
