package com.cricketService.dto.upcoming;

import com.cricketService.entities.match.MatchInfo;
import com.cricketService.enums.MatchType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpComingMatchDto {

    private MatchInfo matchInfo;
    private Long seriesId;
    private MatchType matchType;
    private String seriesName;
    private Long startDate;
    private Long endDate;

}
