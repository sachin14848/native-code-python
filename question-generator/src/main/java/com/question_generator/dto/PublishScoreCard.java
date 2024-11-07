package com.question_generator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.question_generator.dto.score.MatchHeader;
import com.question_generator.dto.score.ScoreCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishScoreCard {

    private List<ScoreCard> scoreCards;
    private MatchHeader matchHeader;

}
