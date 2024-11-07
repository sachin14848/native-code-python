package com.question_generator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {

    private PublishScoreCard ScoreCard;
    private String publishedBy;

}
