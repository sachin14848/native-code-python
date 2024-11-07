package com.cricketService.dto.score.scoreCard.wicketsData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WicketsData {


    private long batId;
    private String batName;
    private int wktNbr;
    private float wktOver;
    private int wktRuns;
    private int ballNbr;

}
