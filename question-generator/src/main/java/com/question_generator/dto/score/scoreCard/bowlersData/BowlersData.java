package com.question_generator.dto.score.scoreCard.bowlersData;

import com.question_generator.dto.score.scoreCard.bowlersData.bowler.Bowler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BowlersData {
    Map<String, Bowler> bowlerMap;
}
