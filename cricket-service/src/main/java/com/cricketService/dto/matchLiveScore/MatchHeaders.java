package com.cricketService.dto.matchLiveScore;

import com.cricketService.dto.TeamsDto;
import com.cricketService.dto.matchLiveScore.matchHeader.MomPlayers;
import com.cricketService.dto.matchLiveScore.matchHeader.MosPlayers;
import com.cricketService.dto.matchLiveScore.matchHeader.TeamDetails;
import com.cricketService.dto.matchLiveScore.miniScore.TossResults;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchHeaders {

    private String state;
    private String Status;
    private String matchFormat;
    private String matchStartTimestamp;
    private TeamDetails teamDetails;
    private MomPlayers momPlayers;
    private MosPlayers mosPlayers;
    private int winningTeamId;
    private String matchEndTimeStamp;
    private int seriesId;
    private String matchDesc;
    private String seriesName;
    private TossResults tossResults;
    private String alertType;
    private TeamsDto team1;
    private TeamsDto team2;


}
