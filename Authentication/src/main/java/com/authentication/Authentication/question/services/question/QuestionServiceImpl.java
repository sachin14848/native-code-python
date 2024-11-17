package com.authentication.Authentication.question.services.question;

import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.order.dto.outcome.OutcomeDto;
import com.authentication.Authentication.order.dto.outcome.SingleOutcome;
import com.authentication.Authentication.order.entity.outcome.Outcome;
import com.authentication.Authentication.order.entity.outcome.Outcomes;
import com.authentication.Authentication.order.repo.OutcomeRepo;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionDto;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionResponseDto;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionUpdateDto;
import com.authentication.Authentication.question.entity.QuestionEntity;
import com.authentication.Authentication.question.entity.QuestionRules;
import com.authentication.Authentication.question.entity.SourceOfTruthEntity;
import com.authentication.Authentication.utiles.ResponseUtil;
import com.authentication.Authentication.utiles.Status;
import com.authentication.Authentication.question.repo.QuestionRepository;
import com.authentication.Authentication.question.services.questionInterface.QuestionRulesService;
import com.authentication.Authentication.question.services.questionInterface.QuestionService;
import com.authentication.Authentication.question.services.questionInterface.SourceOfTruthInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final SourceOfTruthInterface sourceOfTruthInterface;
    private final QuestionRulesService questionRulesService;
    private final OutcomeRepo outcomeRepo;

    @Override
    @Transactional
    public QuestionEntity createQuestion(QuestionDto questionDto) throws BadRequestException {

        SourceOfTruthEntity sourceOfTruth = sourceOfTruthInterface.getSourceOfTruth(questionDto.getSourceOfTruthId());
        QuestionRules questionRules = questionRulesService.getQuestionRules(questionDto.getQuestionRuleId());
        OutcomeDto outcomeDto = questionDto.getOutcome();
        Outcomes outcomes = null;

        Double floorPrice = outcomeDto.getFloorPrice();
        SingleOutcome yes = outcomeDto.getYes();
        SingleOutcome no = outcomeDto.getNo();
        outcomes = Outcomes.builder()
                .yes(Outcome.builder()
                        .id(yes.getId())
                        .outcomeName(yes.getOutcomeName())
                        .share(yes.getShare())
                        .build())
                .floorPrice(floorPrice == null ? 0.05 : floorPrice)
                .liquidity(outcomeDto.getLiquidity())
                .status(Status.OPEN)
                .no(Outcome.builder()
                        .id(no.getId())
                        .outcomeName(no.getOutcomeName())
                        .share(no.getShare())
                        .build())
                .build();

        QuestionEntity entity = QuestionEntity.builder()
                .questionText(questionDto.getQuestionText())
                .outcome(outcomes)
                .isActive(true)
                .subEventId(questionDto.getSubEventId())
                .sourceOfTruth(sourceOfTruth)
                .rule(questionRules)
                .build();

        outcomeRepo.save(outcomes);
        entity.setOutcome(outcomes);
        return questionRepository.save(entity);
    }

    @Override
    @Transactional
    public QuestionEntity updateQuestion(QuestionUpdateDto questionUpdateDto) throws BadRequestException {

        QuestionEntity entity = questionRepository.findById(questionUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("No such Question"));

        if (questionUpdateDto.getSourceOfTruthId() != null) {
            SourceOfTruthEntity sourceOfTruth = sourceOfTruthInterface.getSourceOfTruth(questionUpdateDto.getSourceOfTruthId());
            entity.setSourceOfTruth(sourceOfTruth);
        }
        Long ruleId = questionUpdateDto.getQuestionRuleId();
        if (ruleId != null) {
            QuestionRules questionRules = questionRulesService.getQuestionRules(ruleId);
            entity.setRule(questionRules);

        }
        Long eventId = questionUpdateDto.getEventId();
        if (questionUpdateDto.getQuestionText() != null) {
            entity.setQuestionText(questionUpdateDto.getQuestionText());
        }
        if (questionUpdateDto.getIsActive() != null) {
            entity.setIsActive(questionUpdateDto.getIsActive());
        }

        if (questionUpdateDto.getOutcome() != null) {
            Outcomes outcomes = updateOutcome(questionUpdateDto.getOutcome());
            entity.setOutcome(outcomes);
        }

        return questionRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No such Question"));
        entity.setIsActive(false);
        entity.setDeletedAt(LocalDateTime.now());
        questionRepository.save(entity);
    }

    @Override
    public QuestionEntity getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No such Question"));
    }

    @Override
    public PaginatedResponse<QuestionResponseDto> getAllQuestion(PaginatedReq paginatedReq) {
        int page = paginatedReq.getPage();
        int limit = paginatedReq.getLimit();
        Pageable pageable = PageRequest.of(page, limit);
        Page<QuestionEntity> sourcePage = questionRepository.findByIsActive(paginatedReq.getIsActive(), pageable);
        return ResponseUtil.getPaginatedResponse(sourcePage, QuestionResponseDto::new);
    }

    @Override
    public List<QuestionEntity> getAllQuestionByIds(List<Long> ids) {
        return questionRepository.findAllById(ids);
    }

    @Transactional
    public void announceAnswer(Long questionId, Long outcomeId) {
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        if (question.getStatus() == Status.CLOSED) {
            throw new IllegalStateException("Question has already been closed.");
        }
        Outcomes outcomes = question.getOutcome();
        Outcome outcome = null;
        if (outcomes.getYes().getId() == outcomeId) {
            outcome = outcomes.getYes();
        } else if (outcomes.getNo().getId() == outcomeId) {
            outcome = outcomes.getNo();
        }
        question.setResult(outcome);
        question.setStatus(Status.CLOSED);
        question.setAnnouncedAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public QuestionResponseDto getQuestionWithoutRelatedEntity(Long questionId) {
        return questionRepository.findQuestionSummaryById(questionId).map(QuestionResponseDto::new)
                .orElseThrow(() -> new RuntimeException("Question Not Found By " + questionId));
    }


    private Outcomes updateOutcome(OutcomeDto outcome) {
        Outcomes outcomes = outcomeRepo.findById(outcome.getId())
                .orElseThrow(() -> new RuntimeException("No such outcome"));

//        if (outcome.getYes() != null) {
////            Outcome yes = outcome.getYes();
//            outcomes.setYes(Outcome.builder()
//                    .id(yes.getId())
//                    .outcomeName(yes.getOutcomeName())
//                    .build());
//        }
//        if (outcome.getNo() != null) {
//            Outcome no = outcome.getNo();
//            outcomes.setNo(Outcome.builder()
//                    .id(no.getId())
//                    .outcomeName(no.getOutcomeName())
//                    .build());
//        }
        return outcomeRepo.save(outcomes);
    }

}
