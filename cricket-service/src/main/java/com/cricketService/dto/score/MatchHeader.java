package com.cricketService.dto.score;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchHeader {
    private long matchId;
    private String matchDescription;
    private String matchFormat;
    private String matchType;
    private boolean complete;
    private boolean domestic;
    private long matchStartTimestamp;
    private long matchCompleteTimestamp;
    private boolean dayNight;
    private int year;
    private int dayNumber;
    private String state;
    private String status;
}
