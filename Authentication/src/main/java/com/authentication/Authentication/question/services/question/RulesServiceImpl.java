package com.authentication.Authentication.question.services.question;

import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.RulesDto;
import com.authentication.Authentication.question.dto.res.RulesResDto;
import com.authentication.Authentication.question.dto.res.RulesUpdateDto;
import com.authentication.Authentication.question.entity.Rules;
import com.authentication.Authentication.question.repo.RulesRepository;
import com.authentication.Authentication.question.services.questionInterface.RulesService;
import com.authentication.Authentication.utiles.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RulesServiceImpl implements RulesService {

    private final RulesRepository rulesRepository;

    @Override
    @Transactional
    public Rules createRules(RulesDto rulesDto) {
        Rules rules = Rules.builder()
                .rule(rulesDto.getRule())
                .ruleDesc(rulesDto.getRuleDesc())
                .isActive(true)
                .build();
        return rulesRepository.save(rules);
    }

    @Override
    @Transactional
    public Rules updateRules(RulesUpdateDto rulesUpdateDto) {
        Rules rules = rulesRepository.findById(rulesUpdateDto.getId()).orElseThrow(() -> new RuntimeException("No such rules"));
        if (rulesUpdateDto.getRuleDesc() != null)
            rules.setRuleDesc(rulesUpdateDto.getRuleDesc());
        if (rulesUpdateDto.getRule() != null)
            rules.setRule(rulesUpdateDto.getRule());
        if (rulesUpdateDto.getIsActive() != null)
            rules.setIsActive(rulesUpdateDto.getIsActive());
        return rulesRepository.save(rules);
    }

    @Override
    @Transactional
    public void deleteRules(Long id) {
        Rules rules = rulesRepository.findById(id).orElseThrow(() -> new RuntimeException("No such rules"));
        rules.setIsActive(false);
        rules.setDeletedAt(LocalDateTime.now());
        rulesRepository.save(rules);
    }

    @Override
    public Rules getRules(Long id) {
        return rulesRepository.findById(id).orElseThrow(() -> new RuntimeException("No such rules"));
    }

    @Override
    public PaginatedResponse<RulesResDto> getAllRules(PaginatedReq paginatedReq) {
        int page = paginatedReq.getPage();
        int limit = paginatedReq.getLimit();
        Pageable pageable = PageRequest.of(page, limit);
        Page<Rules> sourcePage = rulesRepository.findByIsActive(paginatedReq.getIsActive(), pageable);
        return ResponseUtil.getPaginatedResponse(sourcePage, RulesResDto::new);
    }

    @Override
    public List<Rules> getAllRulesByIds(List<Long> ids) {
        return rulesRepository.findAllById(ids);
    }

}
