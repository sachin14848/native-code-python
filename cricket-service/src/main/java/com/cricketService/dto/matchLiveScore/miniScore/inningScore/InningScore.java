package com.cricketService.dto.matchLiveScore.miniScore.inningScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InningScore {

    private int inningsId;
    private int batTeamId;
    private String batTeamShortName;
    private int runs;
    private int wickets;
    private int overs;
    private int target;
    private String balls;

}
