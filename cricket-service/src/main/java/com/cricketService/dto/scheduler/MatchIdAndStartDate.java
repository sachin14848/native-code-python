package com.cricketService.dto.scheduler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchIdAndStartDate {

    private Long matchId;
    private Long startDate;
    private String seriesName;

}
