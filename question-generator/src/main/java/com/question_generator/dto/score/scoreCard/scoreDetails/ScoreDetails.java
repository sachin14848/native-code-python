package com.question_generator.dto.score.scoreCard.scoreDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreDetails {
    private int ballNbr;
    private boolean isDeclared;
    private boolean isFollowOn;
    private float overs;
    private int revisedOvers;
    private float runRate;
    private int runs;
    private int wickets;
    private float runsPerBall;
}
