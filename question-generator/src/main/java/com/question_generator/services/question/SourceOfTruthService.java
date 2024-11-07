package com.question_generator.services.question;

import com.question_generator.dto.PaginatedReq;
import com.question_generator.dto.PaginatedResponse;
import com.question_generator.dto.question.SourceOfTruthDto;
import com.question_generator.dto.question.SourceOfTruthUpdateDto;
import com.question_generator.dto.question.res.SourceOfTruthResponseDto;
import com.question_generator.entity.question.SourceOfTruthEntity;
import com.question_generator.repo.question.SourceOfTruthRepository;
import com.question_generator.services.questionInterface.SourceOfTruthInterface;
import com.question_generator.utils.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SourceOfTruthService implements SourceOfTruthInterface {

    private final SourceOfTruthRepository sourceOfTruthRepository;

    @Override
    @Transactional
    public SourceOfTruthEntity createSourceOfTruth(SourceOfTruthDto sourceOfTruthDto) {
        SourceOfTruthEntity entity = SourceOfTruthEntity.builder()
                .sourceOfTruth(sourceOfTruthDto.getSourceOfTruth())
                .sourceOfTruthDesc(sourceOfTruthDto.getSourceOfTruthDesc())
                .sourceOfTruthUrl(sourceOfTruthDto.getSourceOfTruthUrl())
                .isActive(sourceOfTruthDto.getIsActive())
                .build();
        sourceOfTruthRepository.save(entity);
        return entity;
    }

    @Override
    public List<SourceOfTruthResponseDto> getAllSourceOfTruth() {
        return sourceOfTruthRepository.findAll().stream()
                .map(SourceOfTruthResponseDto::new)
                .toList();
    }

    @Override
    public PaginatedResponse<SourceOfTruthResponseDto> getPaginatedSourceOfTruth(PaginatedReq paginatedReq) {
        int page = paginatedReq.getPage();
        int limit = paginatedReq.getLimit();
        Pageable pageable = PageRequest.of(page, limit);
        Page<SourceOfTruthEntity> sourcePage = sourceOfTruthRepository.findByIsActive(paginatedReq.getIsActive(), pageable);
        return ResponseUtil.getPaginatedResponse(sourcePage, SourceOfTruthResponseDto::new);
    }

    @Override
    @Transactional
    public SourceOfTruthEntity updateSourceOfTruth(SourceOfTruthUpdateDto sourceOfTruthUpdateDto) {
        SourceOfTruthEntity sourceOfTruth = sourceOfTruthRepository.findById(sourceOfTruthUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("Source of truth not found with id " + sourceOfTruthUpdateDto.getId()));
        sourceOfTruth.setSourceOfTruth(sourceOfTruthUpdateDto.getSourceOfTruth());
        sourceOfTruth.setSourceOfTruthDesc(sourceOfTruthUpdateDto.getSourceOfTruthDesc());
        sourceOfTruth.setSourceOfTruthUrl(sourceOfTruthUpdateDto.getSourceOfTruthUrl());
        sourceOfTruth.setIsActive(sourceOfTruthUpdateDto.getIsActive());
        return sourceOfTruthRepository.save(sourceOfTruth);
    }

    @Override
    @Transactional
    public void deleteSourceOfTruth(Long id) {
        SourceOfTruthEntity sourceOfTruth = sourceOfTruthRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Source of truth not found with id " + id));
        sourceOfTruth.setIsActive(false);
        sourceOfTruth.setDeletedAt(LocalDateTime.now());
        sourceOfTruthRepository.save(sourceOfTruth);
    }
}
