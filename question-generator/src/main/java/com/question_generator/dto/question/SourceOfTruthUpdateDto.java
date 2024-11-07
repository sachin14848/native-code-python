package com.question_generator.dto.question;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SourceOfTruthUpdateDto {

    private Long id;
    private String sourceOfTruth;
    private String sourceOfTruthDesc;
    private URL sourceOfTruthUrl;
    private Boolean isActive;

}
