package com.question_generator.controller.event;

import com.question_generator.dto.CommonResponse;
import com.question_generator.dto.PaginatedReq;
import com.question_generator.dto.PaginatedResponse;
import com.question_generator.dto.event.SubEventDto;
import com.question_generator.dto.event.SubEventResponseDto;
import com.question_generator.dto.event.SubEventUpdateDto;
import com.question_generator.entity.event.SubEventEntity;
import com.question_generator.services.event.SubEventService;
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

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/ques/event/sub-event")
public class SubEventController {

    private final SubEventService subEventService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse<SubEventEntity>> createSubEvent(@Valid @RequestBody SubEventDto subEventDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            SubEventEntity subEvent = subEventService.createSubEvent(subEventDto);
            return ResponseUtil.buildCommonSuccessResponse(subEvent, "SubEvent created Successfully");
        } catch (BadRequestException e) {
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(errors, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    private ResponseEntity<CommonResponse<PaginatedResponse<SubEventResponseDto>>> getPaginatedResponse(
            @ModelAttribute PaginatedReq paginatedReq
    ) {
        try {
            PaginatedResponse<SubEventResponseDto> data = subEventService.getAllSubEvent(paginatedReq);
            return ResponseUtil.buildSuccessResponse(data, "Data successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildPaginatedErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/")
    private ResponseEntity<CommonResponse<SubEventEntity>> updateSubEvent(@RequestBody SubEventUpdateDto subEventUpdateDto) {
        try {
            SubEventEntity data = subEventService.updateSubEvent(subEventUpdateDto);
            return ResponseUtil.buildCommonSuccessResponse(data, "Updated Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    private ResponseEntity<CommonResponse<Void>> deleteSubEvent(@RequestParam Long id) {
        try {
            subEventService.deleteSubEvent(id);
            return ResponseUtil.buildCommonSuccessResponse(null, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<CommonResponse<SubEventEntity>> getSubEventById(@PathVariable("id") Long id) {
        try {
            SubEventEntity SubEvent = subEventService.getSubEvent(id);
            return ResponseUtil.buildCommonSuccessResponse(SubEvent, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse<List<SubEventEntity>>> getList(@RequestParam("ids") List<Long> ids) {
        try {
            List<SubEventEntity> SubEventList = subEventService.getAllSubEventByIds(ids);
            return ResponseUtil.buildCommonSuccessResponse(SubEventList, "List successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{eventId}/event")
    private ResponseEntity<CommonResponse<PaginatedResponse<SubEventResponseDto>>> getSubEventByEventId(
            @ModelAttribute PaginatedReq paginatedReq, @PathVariable("eventId") Long eventId
    ) {
        try {
            PaginatedResponse<SubEventResponseDto> data = subEventService.getSubEventByEventId(paginatedReq, eventId);
            return ResponseUtil.buildSuccessResponse(data, "Data successfully retrieved");
        } catch (Exception e) {
            return ResponseUtil.buildPaginatedErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
