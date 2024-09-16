package com.cricketService.dto.matchLiveScore.miniScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TossResults {
    private int tossWinnerId;
    private String tossWinnerName;
    private String decision;
}
