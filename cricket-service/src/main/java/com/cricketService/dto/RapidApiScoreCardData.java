package com.cricketService.dto;

import com.cricketService.dto.score.MatchHeader;
import com.cricketService.dto.score.ScoreCard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RapidApiScoreCardData {

    private ScoreCard[] scoreCard;
    private MatchHeader matchHeader;

}
