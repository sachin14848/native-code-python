package com.order.order.controller.order;

import com.order.order.dto.CommonResponse;
import com.order.order.dto.outcome.BuyDto;
import com.order.order.dto.outcome.OutcomeDto;
import com.order.order.entity.outcome.Outcomes;
import com.order.order.services.order.OrderService;
import com.order.order.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order/v1/admin")
public class BuyOrderController {

    private final OrderService orderService;

    @PostMapping(value = "/")
    public ResponseEntity<CommonResponse<Outcomes>> createOrder(@Valid @RequestBody OutcomeDto outcomeDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            Outcomes data = orderService.createOutcome(outcomeDto);
            if (data == null) {
                throw new IllegalArgumentException("Something want wrong");
            }
            System.out.println("Order is : {}" + data);
            return ResponseUtil.buildCommonSuccessResponse(data, "Question created Successfully");
        } catch (BadRequestException e) {
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(errors, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/buy", consumes = "application/json")
    public ResponseEntity<CommonResponse<Outcomes>> getOrders(@Valid @RequestBody BuyDto buyDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException("Validation Error");
            }
            Outcomes data = orderService.buy(buyDto);
            return ResponseUtil.buildCommonSuccessResponse(data, "Question created Successfully");
        } catch (BadRequestException e) {
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtil.buildListErrorResponse(errors, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
