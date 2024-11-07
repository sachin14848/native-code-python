package com.question_generator.dto.score.scoreCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatTeamDetails {
    private Long batTeamId;
    private String batTeamName;
    private String batTeamShortName;
    private Map<String, Batsman> batsmenData;
}
