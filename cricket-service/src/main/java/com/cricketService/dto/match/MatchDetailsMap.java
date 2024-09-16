package com.cricketService.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchDetailsMap {

    private String key;
    private int seriesId;
    private List<Match> match;

}
