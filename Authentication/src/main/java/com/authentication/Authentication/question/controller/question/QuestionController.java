package com.authentication.Authentication.question.controller.question;

import com.authentication.Authentication.cammonDto.CommonResponse;
import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionDto;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionResponseDto;
import com.authentication.Authentication.question.dto.generateQuestion.QuestionUpdateDto;
import com.authentication.Authentication.question.entity.QuestionEntity;
import com.authentication.Authentication.question.services.questionInterface.QuestionService;
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
@RequestMapping("/ques/v1/generate")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<CommonResponse<QuestionResponseDto>> createQuestion(@Valid @RequestBody QuestionDto questionDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            QuestionEntity question = questionService.createQuestion(questionDto);
            if (question == null) {
                throw new IllegalArgumentException("Something want wrong");
            }
            QuestionResponseDto data = new QuestionResponseDto(question);
            return ResponseUtil.buildCommonSuccessResponse(data, "Question created Successfully");
        } catch (BadRequestException e) {
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(errors, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    private ResponseEntity<CommonResponse<PaginatedResponse<QuestionResponseDto>>> getPaginatedResponse(
            @ModelAttribute PaginatedReq paginatedReq
    ) {
        try {
            PaginatedResponse<QuestionResponseDto> data = questionService.getAllQuestion(paginatedReq);
            return ResponseUtil.buildSuccessResponse(data, "Data successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildPaginatedErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/")
    private ResponseEntity<CommonResponse<QuestionEntity>> updateQuestion(@RequestBody QuestionUpdateDto questionUpdateDto) {
        try {
            QuestionEntity data = questionService.updateQuestion(questionUpdateDto);
            return ResponseUtil.buildCommonSuccessResponse(data, "Updated Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    private ResponseEntity<CommonResponse<Void>> deleteQuestion(@RequestParam Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseUtil.buildCommonSuccessResponse(null, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<CommonResponse<QuestionEntity>> getQuestionById(@PathVariable("id") Long id) {
        try {
            QuestionEntity question = questionService.getQuestion(id);
            return ResponseUtil.buildCommonSuccessResponse(question, "Retrieve Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<QuestionEntity>>> getList(@RequestParam("ids") List<Long> ids) {
        try {
            List<QuestionEntity> questionList = questionService.getAllQuestionByIds(ids);
            return ResponseUtil.buildCommonSuccessResponse(questionList, "List successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/{id}/event")
//    private ResponseEntity<CommonResponse<List<QuestionEntity>>> getQuestionByEventId(@PathVariable("id") Long id) {
//        try {
//            List<QuestionEntity> question = questionService.getQuestionByEventId(id);
//            return ResponseUtil.buildCommonSuccessResponse(question, "Question retrieve Successfully");
//        } catch (Exception e) {
//            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{id}/entity")
    private ResponseEntity<CommonResponse<QuestionResponseDto>> getQuestionWithoutEntity(@PathVariable("id") Long id) {
        try {
            QuestionResponseDto question = questionService.getQuestionWithoutRelatedEntity(id);
            return ResponseUtil.buildCommonSuccessResponse(question, "Question retrieve Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/announced")
    private ResponseEntity<CommonResponse<Void>> announceQuestion(@PathVariable("id") Long i, @RequestParam Long outcomeId) {
        try {
            questionService.announceAnswer(i, outcomeId);
            return ResponseUtil.buildCommonSuccessResponse(null, "Question announced Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
