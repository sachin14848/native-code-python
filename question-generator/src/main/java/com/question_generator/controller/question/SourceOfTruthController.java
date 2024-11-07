package com.question_generator.controller.question;

import com.question_generator.dto.CommonResponse;
import com.question_generator.dto.PaginatedReq;
import com.question_generator.dto.PaginatedResponse;
import com.question_generator.dto.question.SourceOfTruthDto;
import com.question_generator.dto.question.SourceOfTruthUpdateDto;
import com.question_generator.dto.question.res.SourceOfTruthResponseDto;
import com.question_generator.entity.question.SourceOfTruthEntity;
import com.question_generator.services.questionInterface.SourceOfTruthInterface;
import com.question_generator.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ques/v1/source-of-truth")
public class SourceOfTruthController {

    private final SourceOfTruthInterface sourceOfTruth;

    @PostMapping("/")
    private ResponseEntity<CommonResponse<SourceOfTruthEntity>> createSourceOfTruth(@Valid @RequestBody SourceOfTruthDto sourceOfTruthDto, BindingResult result) {
        CommonResponse<SourceOfTruthEntity> res = new CommonResponse<>();
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            SourceOfTruthEntity data = sourceOfTruth.createSourceOfTruth(sourceOfTruthDto);
            return ResponseUtil.buildCommonSuccessResponse(data, "Created Successfully");
        } catch (BadRequestException e) {
            List<String> errors = result.getFieldErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(errors, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    private ResponseEntity<CommonResponse<PaginatedResponse<SourceOfTruthResponseDto>>> getPaginatedResponse(
            @ModelAttribute PaginatedReq paginatedReq
    ) {
        try {
            PaginatedResponse<SourceOfTruthResponseDto> data = sourceOfTruth.getPaginatedSourceOfTruth(paginatedReq);
            return ResponseUtil.buildSuccessResponse(data, "Data successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildPaginatedErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/")
    private ResponseEntity<CommonResponse<SourceOfTruthEntity>> updateSourceOfTruth(@RequestBody SourceOfTruthUpdateDto sourceOfTruthDto) {
        try {
            SourceOfTruthEntity sourceOfTruthEntity = sourceOfTruth.updateSourceOfTruth(sourceOfTruthDto);
            return ResponseUtil.buildCommonSuccessResponse(sourceOfTruthEntity, "Updated Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    private ResponseEntity<CommonResponse<Void>> deleteSourceOfTruth(@RequestParam Long id) {
        try {
            sourceOfTruth.deleteSourceOfTruth(id);
            return ResponseUtil.buildCommonSuccessResponse(null, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
