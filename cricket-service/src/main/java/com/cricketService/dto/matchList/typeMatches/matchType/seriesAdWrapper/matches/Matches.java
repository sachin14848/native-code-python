package com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.matches;

import com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.matches.matchInfo.MatchInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Matches {

    private MatchInfo matchInfo;

}
