package com.cricketService.dto.matchLiveScore.matchHeader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDetails {

    private int batTeamId;
    private String batTeamName;
    private int bowlTeamId;
    private String bowlTeamName;

}
