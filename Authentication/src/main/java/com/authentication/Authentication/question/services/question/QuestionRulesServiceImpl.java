package com.authentication.Authentication.question.services.question;

import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.questionRules.QuestionRulesDto;
import com.authentication.Authentication.question.dto.questionRules.QuestionRulesResDto;
import com.authentication.Authentication.question.dto.questionRules.UpdateQuestionRulesDto;
import com.authentication.Authentication.question.entity.QuestionRules;
import com.authentication.Authentication.question.entity.Rules;
import com.authentication.Authentication.question.repo.QuestionRulesRepository;
import com.authentication.Authentication.question.services.questionInterface.QuestionRulesService;
import com.authentication.Authentication.question.services.questionInterface.RulesService;
import com.authentication.Authentication.utiles.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class QuestionRulesServiceImpl implements QuestionRulesService {

    private final QuestionRulesRepository questionRulesRepository;
    private final RulesService rulesService;

    @Override
    @Transactional
    public QuestionRules createQuestionRules(QuestionRulesDto dto) {
        List<Rules> rulesList = rulesService.getAllRulesByIds(dto.getRuleIds());
        QuestionRules questionRules = QuestionRules.builder()
                .notes(dto.getNotes())
                .isActive(true)
                .rules(rulesList)
                .expiryTime(dto.getExpiryTime())
                .settlement(dto.getSettlement())
                .build();

        for (Rules rule : rulesList) {
            rule.getQuestions().add(questionRules);
        }

        return questionRulesRepository.save(questionRules);
    }

    @Override
    @Transactional
    public QuestionRules updateQuestionRules(UpdateQuestionRulesDto dto) {
        QuestionRules existingQuestionRules = questionRulesRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("QuestionRules not found with id: " + dto.getId()));

        // Update fields in existing QuestionRules with the new data
        existingQuestionRules.setExpiryTime(dto.getExpiryTime());
        existingQuestionRules.setSettlement(dto.getSettlement());
        existingQuestionRules.setNotes(dto.getNotes());
        existingQuestionRules.setIsActive(dto.getIsActive());

        List<Rules> rulesList = rulesService.getAllRulesByIds(dto.getRuleIds());
        List<Rules> existingRules = new ArrayList<>(existingQuestionRules.getRules());
        for (Rules existingRule : existingRules) {
            if (!rulesList.contains(existingRule)) {
                existingRule.getQuestions().remove(existingQuestionRules);  // Update the bidirectional relationship
            }
        }

        // Set the new list of Rules for the QuestionRules
        existingQuestionRules.setRules(rulesList);

        for (Rules rule : rulesList) {
            if (!rule.getQuestions().contains(existingQuestionRules)) {
                rule.getQuestions().add(existingQuestionRules);
            }
        }

        return questionRulesRepository.save(existingQuestionRules);
    }

    @Override
    @Transactional
    public void deleteQuestionRules(Long id) {
        QuestionRules questionRules = questionRulesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionRules not found with id: " + id));

        // Mark it as deleted by setting deletedAt to the current timestamp
        questionRules.setDeletedAt(LocalDateTime.now());
        questionRules.setIsActive(false);  // Mark it as inactive to prevent further updates or deletion

        // Save the changes
        questionRulesRepository.save(questionRules);

    }

    @Override
    @Transactional
    public QuestionRules getQuestionRules(Long id) {
        return questionRulesRepository.findById(          id)
                .orElseThrow(() -> new RuntimeException("QuestionRules not found with id: " + id));
    }

    @Override
    public PaginatedResponse<QuestionRulesResDto> getPaginatedQuestionRules(PaginatedReq paginatedReq) {
        int page = paginatedReq.getPage();
        int limit = paginatedReq.getLimit();
        Pageable pageable = PageRequest.of(page, limit);
        Page<QuestionRules> sourcePage = questionRulesRepository.findByIsActive(paginatedReq.getIsActive(), pageable);
        return ResponseUtil.getPaginatedResponse(sourcePage, QuestionRulesResDto::new);
    }

    @Override
    public List<QuestionRulesResDto> getAllQuestionRules(List<Long> ids) {
        return questionRulesRepository.findAllById(ids).stream().map(QuestionRulesResDto::new).toList();
    }
}
