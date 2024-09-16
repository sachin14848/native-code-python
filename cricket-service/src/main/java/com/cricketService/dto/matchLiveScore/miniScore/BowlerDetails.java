package com.cricketService.dto.matchLiveScore.miniScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BowlerDetails {

    private int id;
    private String overs;
    private String economy;
    private int wickets;
    private String name;
    private int runs;

}
