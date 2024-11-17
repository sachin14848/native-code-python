package com.authentication.Authentication.question.services.questionInterface;

import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.SourceOfTruthDto;
import com.authentication.Authentication.question.dto.SourceOfTruthUpdateDto;
import com.authentication.Authentication.question.dto.res.SourceOfTruthResponseDto;
import com.authentication.Authentication.question.entity.SourceOfTruthEntity;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface SourceOfTruthInterface {

    SourceOfTruthEntity createSourceOfTruth(SourceOfTruthDto sourceOfTruthDto);

    List<SourceOfTruthResponseDto> getAllSourceOfTruth();

    PaginatedResponse<SourceOfTruthResponseDto> getPaginatedSourceOfTruth(PaginatedReq paginatedReq);

    SourceOfTruthEntity updateSourceOfTruth(SourceOfTruthUpdateDto sourceOfTruthUpdateDto);

    void deleteSourceOfTruth(Long id);

    SourceOfTruthEntity getSourceOfTruth(Long id) throws BadRequestException;

}