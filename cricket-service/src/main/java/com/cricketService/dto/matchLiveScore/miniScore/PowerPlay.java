package com.cricketService.dto.matchLiveScore.miniScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PowerPlay {

    private int id;
    private float ovrFrom;
    private int ovrTo;
    private String ppType;
    private int run;


}
