package com.question_generator.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class SourceOfTruthDto {

    @NotNull(message = "Source of truth is required")
    private String sourceOfTruth;

    @NotNull(message = "Source of truth Description is required")
    @Pattern(
            regexp = "(?:\\S+\\s+){10,}\\S+",
            message = "Description must contain at least 25 words"
    )
    private String sourceOfTruthDesc;

    @NotNull(message = "Source of truth url is required")
    private URL sourceOfTruthUrl;

    @NotNull(message = "Source of truth isActive is required")
    private Boolean isActive;

}
