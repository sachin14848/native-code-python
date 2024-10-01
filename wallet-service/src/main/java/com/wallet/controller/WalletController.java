package com.wallet.controller;

import com.wallet.dto.CommonResponse;
import com.wallet.services.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/wallet")
public class WalletController {

    private static final Logger log = LoggerFactory.getLogger(WalletController.class);
    private final WalletService walletService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<CommonResponse<Boolean>> createAccount(@PathVariable Long userId, HttpServletRequest req) {

        log.info("User Id : {}", userId);
        try {
            if (userId == null) {
                throw new Exception("User Id not found");
            }
            boolean success = walletService.createAccount(userId);
            if (!success) {
                throw new Exception("failed to create account");
            }
            CommonResponse<Boolean> response = CommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.OK.value())
                    .status("success")
                    .data(true)
                    .message("User account created successfully")
                    .error(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(CommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status("error")
                    .success(false)
                    .message("Failed to create a new account")
                    .data(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .error(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
