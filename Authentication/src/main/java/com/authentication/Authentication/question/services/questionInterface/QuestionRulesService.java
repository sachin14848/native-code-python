package com.authentication.Authentication.question.services.questionInterface;

import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.entity.QuestionRules;
import com.authentication.Authentication.question.dto.questionRules.QuestionRulesDto;
import com.authentication.Authentication.question.dto.questionRules.QuestionRulesResDto;
import com.authentication.Authentication.question.dto.questionRules.UpdateQuestionRulesDto;

import java.util.List;

public interface QuestionRulesService {

    QuestionRules createQuestionRules(QuestionRulesDto dto);

    QuestionRules updateQuestionRules(UpdateQuestionRulesDto dto);

    void deleteQuestionRules(Long id);

    QuestionRules getQuestionRules(Long id);

    PaginatedResponse<QuestionRulesResDto> getPaginatedQuestionRules(PaginatedReq paginatedReq);

    List<QuestionRulesResDto> getAllQuestionRules(List<Long> ids);

}
