package com.cricketService.dto.matchList.typeMatches;

import com.cricketService.dto.matchList.typeMatches.matchType.SeriesMatches;
import com.cricketService.enums.MatchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeMatches {
    private MatchType matchType;
    private List<SeriesMatches> seriesMatches;
}
