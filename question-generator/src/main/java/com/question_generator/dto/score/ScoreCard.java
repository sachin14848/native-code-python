package com.question_generator.dto.score;

import com.question_generator.dto.score.scoreCard.BatTeamDetails;
import com.question_generator.dto.score.scoreCard.BowlTeamDetails;
import com.question_generator.dto.score.scoreCard.extrasData.ExtrasData;
import com.question_generator.dto.score.scoreCard.partnershipsData.PartnershipsData;
import com.question_generator.dto.score.scoreCard.scoreDetails.ScoreDetails;
import com.question_generator.dto.score.scoreCard.wicketsData.WicketsData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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



}
