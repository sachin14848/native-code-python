package com.cricketService.dto.score.scoreCard;

import com.cricketService.dto.score.scoreCard.bowlersData.BowlersData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BowlTeamDetails {

    private long bowlTeamId;
    private String bowlTeamName;
    private String bowlTeamShortName;
    private Map<String, BowlersData> bowlersData;
}
