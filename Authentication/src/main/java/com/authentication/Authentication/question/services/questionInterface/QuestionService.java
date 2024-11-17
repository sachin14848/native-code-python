package com.authentication.Authentication.question.services.questionInterface;

import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionDto;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionResponseDto;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionUpdateDto;
import com.authentication.Authentication.question.entity.QuestionEntity;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface QuestionService {

    QuestionEntity createQuestion(QuestionDto questionDto) throws BadRequestException;

    QuestionEntity updateQuestion(QuestionUpdateDto questionUpdateDto) throws BadRequestException;

    void deleteQuestion(Long id);

    QuestionEntity getQuestion(Long id);

    PaginatedResponse<QuestionResponseDto> getAllQuestion(PaginatedReq paginatedReq);

    List<QuestionEntity> getAllQuestionByIds(List<Long> ids);

    void announceAnswer(Long questionId, Long outcomeId);

    QuestionResponseDto getQuestionWithoutRelatedEntity(Long questionId);
}
