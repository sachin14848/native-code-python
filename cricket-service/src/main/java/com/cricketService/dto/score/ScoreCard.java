package com.cricketService.dto.score;

import com.cricketService.dto.score.scoreCard.BatTeamDetails;
import com.cricketService.dto.score.scoreCard.BowlTeamDetails;
import com.cricketService.dto.score.scoreCard.extrasData.ExtrasData;
import com.cricketService.dto.score.scoreCard.partnershipsData.PartnershipsData;
import com.cricketService.dto.score.scoreCard.scoreDetails.ScoreDetails;
import com.cricketService.dto.score.scoreCard.wicketsData.WicketsData;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = ScoreCard.class, name = "com.cricketService.dto.score.ScoreCard")
//})
public class ScoreCard {

    private Long matchId;
    private int inningsId;
    private Long timeScore;
    private BatTeamDetails batTeamDetails;
    private BowlTeamDetails bowlTeamDetails;
    private ScoreDetails scoreDetails;
    private ExtrasData extrasData;
    //    private Object ppData;
    private Map<String, WicketsData> wicketsData;
    private Map<String, PartnershipsData> partnershipsData;

//    public ScoreCard() {}

//    @JsonCreator
//    public ScoreCard(@JsonProperty("playerName") Long matchId, @JsonProperty("wicketsData") Map<String, WicketsData> wicketsData) {
//        this.matchId = matchId;
//        this.wicketsData = wicketsData;
//    }


}
