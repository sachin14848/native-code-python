package com.cricketService.dto.score.scoreCard.extrasData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtrasData {

    private int noBalls;
    private int total;
    private int byes;
    private int penalty;
    private int wides;
    private int legByes;

}
