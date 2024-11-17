package com.authentication.Authentication.question.services.questionInterface;

import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.RulesDto;
import com.authentication.Authentication.question.dto.res.RulesResDto;
import com.authentication.Authentication.question.dto.res.RulesUpdateDto;
import com.authentication.Authentication.question.entity.Rules;

import java.util.List;

public interface RulesService {

    Rules createRules(RulesDto rulesDto);

    Rules updateRules(RulesUpdateDto rulesUpdateDto);

    void deleteRules(Long id);

    Rules getRules(Long id);

    PaginatedResponse<RulesResDto> getAllRules(PaginatedReq paginatedReq);

    List<Rules> getAllRulesByIds(List<Long> ids);
}
