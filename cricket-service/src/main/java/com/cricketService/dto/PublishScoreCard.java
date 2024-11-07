package com.cricketService.dto;

import com.cricketService.dto.score.MatchHeader;
import com.cricketService.dto.score.ScoreCard;
import com.cricketService.serializer.ScoreCardListDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
