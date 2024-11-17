package com.authentication.Authentication.question.controller.question;

import com.authentication.Authentication.cammonDto.CommonResponse;
import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.questionRules.QuestionRulesDto;
import com.authentication.Authentication.question.dto.questionRules.QuestionRulesResDto;
import com.authentication.Authentication.question.dto.questionRules.UpdateQuestionRulesDto;
import com.authentication.Authentication.question.entity.QuestionRules;
import com.authentication.Authentication.question.services.questionInterface.QuestionRulesService;
import com.authentication.Authentication.utiles.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ques/v1/question-rules")
public class QuestionRulesController {

    private final QuestionRulesService questionRulesService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse<QuestionRulesResDto>> createRules(@Valid @RequestBody QuestionRulesDto rulesDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            QuestionRules rule = questionRulesService.createQuestionRules(rulesDto);
            QuestionRulesResDto data = new QuestionRulesResDto(rule);
            return ResponseUtil.buildCommonSuccessResponse(data, "Rules created Successfully");
        } catch (BadRequestException e) {
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(errors, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    private ResponseEntity<CommonResponse<PaginatedResponse<QuestionRulesResDto>>> getPaginatedResponse(
            @ModelAttribute PaginatedReq paginatedReq
    ) {
        try {
            PaginatedResponse<QuestionRulesResDto> data = questionRulesService.getPaginatedQuestionRules(paginatedReq);
            return ResponseUtil.buildSuccessResponse(data, "Data successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildPaginatedErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/")
    private ResponseEntity<CommonResponse<QuestionRules>> updateRules(@RequestBody UpdateQuestionRulesDto rulesUpdateDto) {
        try {
            QuestionRules data = questionRulesService.updateQuestionRules(rulesUpdateDto);
            return ResponseUtil.buildCommonSuccessResponse(data, "Updated Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    private ResponseEntity<CommonResponse<Void>> deleteRules(@RequestParam Long id) {
        try {
            questionRulesService.deleteQuestionRules(id);
            return ResponseUtil.buildCommonSuccessResponse(null, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<CommonResponse<QuestionRulesResDto>> getRulesById(@PathVariable("id") Long id) {
        try {
            QuestionRules rules = questionRulesService.getQuestionRules(id);
            QuestionRulesResDto res = new QuestionRulesResDto(rules);
            return ResponseUtil.buildCommonSuccessResponse(res, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<QuestionRulesResDto>>> getList(@RequestParam("ids") List<Long> ids) {
        try {
            List<QuestionRulesResDto> rulesList = questionRulesService.getAllQuestionRules(ids);
            return ResponseUtil.buildCommonSuccessResponse(rulesList, "List successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
