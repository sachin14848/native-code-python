package com.question_generator.dto.question.generateQuestion;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleOutcome {

    @NotNull
    private Integer id;
    @NotNull
    private String outcomeName;
    @NotNull
    private Double share;

}
