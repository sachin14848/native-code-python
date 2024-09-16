package com.cricketService.dto;

import com.cricketService.dto.matchLiveScore.MatchHeaders;
import com.cricketService.dto.matchLiveScore.MiniScore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapidApiLiveScore {

    private MiniScore miniscore;
    private MatchHeaders matchHeaders;

}
