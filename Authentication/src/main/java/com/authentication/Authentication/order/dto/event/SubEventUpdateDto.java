package com.order.order.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubEventUpdateDto {

    private Long id;
    private Long categoryId;
    private Long eventId;
    private String categoryTitle;

    private Boolean isActive;

}
