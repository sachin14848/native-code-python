package com.cricketService.dto.matchLiveScore.miniScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatsmanDetails {

    private int id;
    private int balls;
    private int runs;
    private int fours;
    private int sixes;
    private String strkRate;
    private String name;
    private String nickName;

}
