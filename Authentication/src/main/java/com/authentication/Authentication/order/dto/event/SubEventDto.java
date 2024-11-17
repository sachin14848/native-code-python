package com.order.order.dto.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubEventDto {

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotNull(message = "Category ID is required")
    private String categoryTitle;

    @NotNull(message = "Is Active is required")
    private Boolean isActive;

}
