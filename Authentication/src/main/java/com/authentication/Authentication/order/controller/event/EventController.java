package com.order.order.controller.event;

import com.order.order.dto.CommonResponse;
import com.order.order.dto.event.EventDto;
import com.order.order.dto.event.EventResponseDto;
import com.order.order.entity.event.EventEntity;
import com.order.order.services.event.EventService;
import com.order.order.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order/v1/events")
public class EventController {

    private final EventService eventService;

    @PostMapping("/create-event")
    private ResponseEntity<CommonResponse<EventResponseDto>> createEvent(@Valid @RequestBody EventDto eventDto, BindingResult result) {
        CommonResponse<EventResponseDto> response = new CommonResponse<>();
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            EventEntity entity = eventService.createEvent(eventDto);
            EventResponseDto data = new EventResponseDto(entity);
            response.setData(data);
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setSuccess(true);
            response.setMessage("Event Created Successfully");
            response.setError(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadRequestException e) {
            List<String> fieldError = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(fieldError, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return ResponseUtil.buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-events")
    private ResponseEntity<CommonResponse<Set<EventResponseDto>>> getAllEvents() {
        CommonResponse<Set<EventResponseDto>> response = new CommonResponse<>();
        try {
            Set<EventResponseDto> data = eventService.getAllEvents();
            if (data.isEmpty()) {
                throw new RuntimeException("No events");
            }
            response.setData(data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return ResponseUtil.buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    private ResponseEntity<CommonResponse<EventEntity>> getEventById(@PathVariable("id") Long id) {
        try {
            EventEntity rules = eventService.getEventById(id);
            return ResponseUtil.buildCommonSuccessResponse(rules, "Deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
