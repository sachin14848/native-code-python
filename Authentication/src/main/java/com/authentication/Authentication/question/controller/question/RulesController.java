package com.authentication.Authentication.question.controller.question;

import com.authentication.Authentication.cammonDto.CommonResponse;
import com.authentication.Authentication.cammonDto.PaginatedReq;
import com.authentication.Authentication.cammonDto.PaginatedResponse;
import com.authentication.Authentication.question.dto.RulesDto;
import com.authentication.Authentication.question.dto.res.RulesResDto;
import com.authentication.Authentication.question.dto.res.RulesUpdateDto;
import com.authentication.Authentication.question.entity.Rules;
import com.authentication.Authentication.question.services.questionInterface.RulesService;
import com.authentication.Authentication.utiles.ResponseUtil;
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

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/ques/v1/rules")
public class RulesController {

    private final RulesService rulesService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse<RulesResDto>> createRules(@Valid @RequestBody RulesDto rulesDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            Rules rule = rulesService.createRules(rulesDto);
            RulesResDto data = new RulesResDto(rule);
            return ResponseUtil.buildCommonSuccessResponse(data, "Rules created Successfully");
        } catch (BadRequestException e) {
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(errors, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    private ResponseEntity<CommonResponse<PaginatedResponse<RulesResDto>>> getPaginatedResponse(
            @ModelAttribute PaginatedReq paginatedReq
    ) {
        try {
            PaginatedResponse<RulesResDto> data = rulesService.getAllRules(paginatedReq);
            return ResponseUtil.buildSuccessResponse(data, "Data successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildPaginatedErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/")
    private ResponseEntity<CommonResponse<Rules>> updateRules(@RequestBody RulesUpdateDto rulesUpdateDto) {
        try {
            Rules data = rulesService.updateRules(rulesUpdateDto);
            return ResponseUtil.buildCommonSuccessResponse(data, "Updated Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    private ResponseEntity<CommonResponse<Void>> deleteRules(@RequestParam Long id) {
        try {
            rulesService.deleteRules(id);
            return ResponseUtil.buildCommonSuccessResponse(null, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<CommonResponse<Rules>> getRulesById(@PathVariable("id") Long id) {
        try {
            Rules rules = rulesService.getRules(id);
            return ResponseUtil.buildCommonSuccessResponse(rules, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<Rules>>> getList(@RequestParam("ids") List<Long> ids) {
        try {
            List<Rules> rulesList = rulesService.getAllRulesByIds(ids);
            return ResponseUtil.buildCommonSuccessResponse(rulesList, "List successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
