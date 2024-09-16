package com.question_generator.controller;

import com.question_generator.dto.CommonResponse;
import com.question_generator.dto.CricketDto;
import com.question_generator.services.CricketQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ques/cricket/")
public class QGeneratorController {

    private final CricketQuestionService questionService;

    @PostMapping("create")
    private ResponseEntity<CommonResponse<?>> createCricketQuestion(@Valid @RequestBody CricketDto cricketDto, BindingResult result) {
        CommonResponse<?> response = new CommonResponse<>();
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.toString());
            }
            questionService.createQuestion(cricketDto);
            response.setSuccess(true);
            response.setMessage("Question created successfully");
            response.setStatusCode(HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (BadRequestException ex) {
            FieldError fieldError = result.getFieldError();
            response.setSuccess(false);
            response.setError("Bad request");
            if (fieldError != null) {
                response.setMessage(fieldError.getDefaultMessage());
            } else {
                response.setMessage("Unknown error");
            }
            response.setData(null);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setData(null);
            response.setSuccess(false);
            response.setError("Internal Server Error");
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    private ResponseEntity<CommonResponse<List<CricketDto>>> getAllCricketQuestions() {
        CommonResponse<List<CricketDto>> response = new CommonResponse<>();
        try {
            List<CricketDto> cricketQuestions = questionService.getCricketQuestions();
            response.setData(cricketQuestions);
            response.setSuccess(true);
            response.setMessage("Question retrieve successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setData(null);
            response.setError(e.getMessage());
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
