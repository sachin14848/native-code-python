package com.order.order.dto.event;

import com.order.order.entity.event.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseDto {

    private Long id;
    private String eventName;
    private String eventDescription;
    private LocalDate createdAt;

    public EventResponseDto(EventEntity eventEntity) {
        this.id = eventEntity.getId();
        this.eventName = eventEntity.getEventName();
        this.eventDescription = eventEntity.getEventDescription();
        this.createdAt = eventEntity.getCreatedAt();
    }
}
