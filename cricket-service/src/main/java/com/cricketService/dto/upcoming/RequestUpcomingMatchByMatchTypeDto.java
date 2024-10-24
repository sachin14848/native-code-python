package com.cricketService.dto.upcoming;

import com.cricketService.enums.MatchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUpcomingMatchByMatchTypeDto {

    private MatchType matchType;

}
