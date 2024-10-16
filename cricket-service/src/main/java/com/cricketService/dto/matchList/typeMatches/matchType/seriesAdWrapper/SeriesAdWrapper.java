package com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper;

import com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.matches.Matches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesAdWrapper {

    private Long seriesId;
    private String seriesName;
    private List<Matches> matches;
}
