package com.question_generator.dto.question.res;

import com.question_generator.entity.question.QuestionEntity;
import com.question_generator.entity.question.SourceOfTruthEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SourceOfTruthResponseDto {

    private Long id;
    private String sourceOfTruth;
    private String sourceOfTruthDesc;
    private URL sourceOfTruthUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public SourceOfTruthResponseDto(SourceOfTruthEntity sourceOfTruthEntity) {
        this.id = sourceOfTruthEntity.getId();
        this.sourceOfTruth = sourceOfTruthEntity.getSourceOfTruth();
        this.sourceOfTruthDesc = sourceOfTruthEntity.getSourceOfTruthDesc();
        this.sourceOfTruthUrl = sourceOfTruthEntity.getSourceOfTruthUrl();
        this.isActive = sourceOfTruthEntity.getIsActive();
        this.createdAt = sourceOfTruthEntity.getCreatedAt();
        this.updatedAt = sourceOfTruthEntity.getUpdatedAt();
        this.deletedAt = sourceOfTruthEntity.getDeletedAt();
    }
//    private List<QuestionEntity> questions;

}
