package com.order.order.dto.event;

import com.order.order.entity.event.EventEntity;
import com.order.order.entity.event.SubEventEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubEventResponseDto {

    private Long id;
    private Long categoryId;
    private String categoryTitle;

    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private EventEntity event;

    public SubEventResponseDto(SubEventEntity subEventEntity) {
        this.id = subEventEntity.getId();
        this.categoryId = subEventEntity.getCategoryId();
        this.categoryTitle = subEventEntity.getCategoryTitle();
        this.isActive = subEventEntity.getIsActive();
        this.createdAt = subEventEntity.getCreatedAt();
        this.updatedAt = subEventEntity.getUpdatedAt();
        this.event = subEventEntity.getEvent();
        this.deletedAt = subEventEntity.getDeletedAt();
    }

}
