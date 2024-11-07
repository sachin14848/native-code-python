package com.question_generator.dto.score.scoreCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Batsman {
    private long batId;
    private String batName;
    private String batShortName;
    private boolean isCaptain;
    private boolean isKeeper;
    private int runs;
    private int balls;
    private int dots;
    private int fours;
    private int sixes;
    private int mins;
    private float strikeRate;
    private String outDesc;
    private long bowlerId;
    private int fielderId1;
    private int fielderId2;
    private int fielderId3;
    private int ones;
    private int twos;
    private int threes;
    private int fives;
    private int boundaries;
    private int sixers;
    private String wicketCode;
    private boolean isOverseas;
    private String inMatchChange;
    private String playingXIChange;
}
