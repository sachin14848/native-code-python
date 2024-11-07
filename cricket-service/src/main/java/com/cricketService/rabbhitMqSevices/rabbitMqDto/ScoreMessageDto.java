package com.cricketService.rabbhitMqSevices.rabbitMqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreMessageDto {
    private String matchId;
    private Object scoreUpdate;
}
