package com.cricketService.dto.match;

import com.cricketService.dto.TeamsDto;
import com.cricketService.dto.VenueName;
import com.cricketService.entities.match.MatchInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchInfoDto implements Serializable {

    private static final long matchInfoBySeries = 1L;

    private int matchId;
    private int seriesId;
    private String seriesName;
    private String matchDesc;
    private String matchFormat;
    private String startDate;
    private String endDate;
    private String state;
    private String status;
    private TeamsDto team1;
    private TeamsDto team2;
    private VenueName venueName;
    private String currBatTeamId;
    private boolean isTimeAnnounced;

    public MatchInfoDto(MatchInfo matchInfo) {
        this.matchId = Math.toIntExact(matchInfo.getId());
        this.seriesId = Math.toIntExact(matchInfo.getSeries().getId());
        this.seriesName = matchInfo.getSeriesName();
        this.matchDesc = matchInfo.getMatchDesc();
        this.matchFormat = matchInfo.getMatchFormat();
        this.startDate = matchInfo.getStartDate();
        this.endDate = matchInfo.getEndDate();
        this.state = matchInfo.getState();
        this.status = matchInfo.getStatus();
        this.team1 = new TeamsDto(matchInfo.getTeam1());
        this.team2 = new TeamsDto(matchInfo.getTeam2());
        this.venueName = new VenueName();
        this.currBatTeamId = matchInfo.getCurrBatTeamId();
        this.isTimeAnnounced = matchInfo.isTimeAnnounced();
    }
}
