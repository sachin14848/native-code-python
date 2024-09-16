package com.question_generator.dto;

import com.question_generator.entity.CricketQuestion;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CricketDto {

    private Long id;

    @NotEmpty(message = "Quantity is not specified")
    @NotNull(message = "Question is not null")
    @Size(min = 25, max = 100, message = "Question is required with Minimum 25 and Maximum 100 words")
    private String question;

    public CricketDto(CricketQuestion cricketQuestion) {
        this.question = cricketQuestion.getQuestion();
        this.id = cricketQuestion.getId();
    }
}
