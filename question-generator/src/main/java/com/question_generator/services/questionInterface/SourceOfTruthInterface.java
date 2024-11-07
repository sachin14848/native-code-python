package com.question_generator.services.questionInterface;

import com.question_generator.dto.PaginatedReq;
import com.question_generator.dto.PaginatedResponse;
import com.question_generator.dto.question.SourceOfTruthDto;
import com.question_generator.dto.question.SourceOfTruthUpdateDto;
import com.question_generator.dto.question.res.SourceOfTruthResponseDto;
import com.question_generator.entity.question.SourceOfTruthEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface SourceOfTruthInterface {

    SourceOfTruthEntity createSourceOfTruth(SourceOfTruthDto sourceOfTruthDto);

    List<SourceOfTruthResponseDto> getAllSourceOfTruth();

    PaginatedResponse<SourceOfTruthResponseDto> getPaginatedSourceOfTruth(PaginatedReq paginatedReq);

    SourceOfTruthEntity updateSourceOfTruth(SourceOfTruthUpdateDto sourceOfTruthUpdateDto);

    void deleteSourceOfTruth(Long id);

}