package com.question_generator.dto.outcome;

import com.question_generator.entity.question.Outcome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOutcomeDto {
    private Long id;
    private Long questionId;
    private Outcome yes;
    private Outcome no;

    private Double liquidity;
    private Double floorPrice;
}
