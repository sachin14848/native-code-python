package com.question_generator.dto.score.scoreCard.bowlersData.bowler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bowler {
    private long bowlerId;
    private String bowlName;
    private String bowlShortName;
    private boolean isCaptain;
    private boolean isKeeper;
    private float overs;
    private int maidens;
    private int runs;
    private int wickets;
    private float economy;
    private int no_balls;
    private int wides;
    private int dots;
    private int balls;
    private float runsPerBall;
}
