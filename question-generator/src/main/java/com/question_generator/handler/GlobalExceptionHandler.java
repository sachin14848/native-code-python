package com.question_generator.handler;

import com.question_generator.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        CommonResponse<?> response = CommonResponse.<Map<String, String>>builder().data(null)
                .error(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Bad Request")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
