package com.apiGetway.controller;

import com.apiGetway.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @RequestMapping(value = "/service-b", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> serviceBFallback() {

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(503)
                .status("Service Unavailable")
                .error("Service B is currently unavailable")
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
