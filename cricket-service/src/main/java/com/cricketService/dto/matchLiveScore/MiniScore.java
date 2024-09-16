package com.cricketService.dto.matchLiveScore;

import com.cricketService.dto.matchLiveScore.miniScore.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniScore {

    private BatsmanDetails batsmanStriker;
    private BatsmanDetails batsmanNonStriker;
    private BowlerDetails bowlerStriker;
    private BowlerDetails bowlerNonStriker;
    private float crr;
    private String inningsNbr;
    private String lastWkt;
    private String curOvsStats;
    private InningsScores inningsScores;
    private int inningsId;
//    private String udrs;
    private String partnership;
    private PP pp;
    private int target;
    private BatTeamScore batTeamScore;
    private String custStatus;
    private String  responseLastUpdated;
    private String event;

}
