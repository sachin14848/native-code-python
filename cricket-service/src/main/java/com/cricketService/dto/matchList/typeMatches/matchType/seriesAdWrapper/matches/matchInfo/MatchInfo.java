package com.cricketService.dto.matchList.typeMatches.matchType.seriesAdWrapper.matches.matchInfo;

import com.cricketService.dto.TeamsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchInfo {

    private Long matchId;
    private Long seriesId;
    private String seriesName;
    private String matchDesc;
    private String matchFormat;
    private String startDate;
    private String endDate;
    private String state;
    private String status;
    private TeamsDto team1;
    private TeamsDto team2;
    private VenueInfo venueInfo;
    private String seriesStartDt;
    private String seriesEndDt;
    private boolean isTimeAnnounced;
    private String stateTitle;


}
