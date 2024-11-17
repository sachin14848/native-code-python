package com.question_generator.dto.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    @NotNull(message = "Event name is required")
    private String eventName;

    @NotNull(message = "Event Description is required")
    @Pattern(
            regexp = "(?:\\S+\\s+){10,}\\S+",
            message = "Description must contain at least 25 words"
    )
    private String eventDesc;

}
