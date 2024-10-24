package com.cricketService.dto.scoreBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RapidScoreCardDto {

    private String battingTeam;
    private String bowlingTeam;

}
