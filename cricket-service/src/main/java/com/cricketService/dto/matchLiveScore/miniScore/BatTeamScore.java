package com.cricketService.dto.matchLiveScore.miniScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatTeamScore {

    private int teamId;
    private int teamScore;
    private int teamWkts;

}
