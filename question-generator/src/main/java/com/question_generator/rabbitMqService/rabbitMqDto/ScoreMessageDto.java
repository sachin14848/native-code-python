package com.question_generator.rabbitMqService.rabbitMqDto;

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