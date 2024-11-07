package com.cricketService.dto;

import com.cricketService.dto.score.MatchHeader;
import com.cricketService.dto.score.ScoreCard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreDto {

    private PublishScoreCard ScoreCard;
    private String publishedBy;

}
