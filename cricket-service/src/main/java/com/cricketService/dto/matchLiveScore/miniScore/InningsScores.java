package com.cricketService.dto.matchLiveScore.miniScore;

import com.cricketService.dto.matchLiveScore.miniScore.inningScore.InningScore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InningsScores {
    private List<InningScore> inningsScore;

}
