package com.cricketService.entities.match;

import com.cricketService.dto.match.MatchInfoDto;
import com.cricketService.entities.Series;
import com.cricketService.entities.TeamsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_info")
public class MatchInfo {

    @Id
    private Long id;

    private String seriesName;
    private String matchDesc;
    private String matchFormat;
    private String startDate;
    private String endDate;
    private String state;
    private String status;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false, referencedColumnName = "id")
    private TeamsEntity team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false, referencedColumnName = "id")
    private TeamsEntity team2;
    //    private VenueName venueName;
    private String currBatTeamId;
    private boolean isTimeAnnounced;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false, referencedColumnName = "id")
    private Series series;

    public MatchInfo(MatchInfoDto matchInfoDto) {
        this.id = (long) matchInfoDto.getMatchId();
        this.seriesName = matchInfoDto.getSeriesName();
        this.matchDesc = matchInfoDto.getMatchDesc();
        this.matchFormat = matchInfoDto.getMatchFormat();
        this.startDate = matchInfoDto.getStartDate();
        this.endDate = matchInfoDto.getEndDate();
        this.state = matchInfoDto.getState();
        this.status = matchInfoDto.getStatus();
        this.team1.setTeamId(matchInfoDto.getTeam1().getTeamId());
        this.team1 = new TeamsEntity(matchInfoDto.getTeam1());
        this.team2 = new TeamsEntity(matchInfoDto.getTeam2());
    }
}
