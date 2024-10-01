package com.authentication.Authentication.controller;

import com.authentication.Authentication.dto.NewCommonResponse;
import com.authentication.Authentication.dto.user.SignUpDto;
import com.authentication.Authentication.dto.user.SignUpWithReferralCodeDto;
import com.authentication.Authentication.services.SignUpService;
import com.authentication.Authentication.services.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SignUpController {

    private final SignUpService signUpService;
    private final WalletService walletService;

    @PostMapping("/sign-up")
    public ResponseEntity<NewCommonResponse<Boolean>> signUp(@Valid @RequestBody SignUpDto signUpDto,
                                                             BindingResult result, HttpServletRequest req) {
        try {
            System.out.println("SERVICE : " + signUpService);
            if (result.hasErrors()) {
                throw new Exception("Error Creating new User");
            }
            signUpService.signUpWithoutReferralCode(signUpDto);
            NewCommonResponse<Boolean> response = NewCommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.OK.value())
                    .status("success")
                    .data(true)
                    .message("User updated successfully")
                    .error(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            FieldError fieldError = result.getFieldError();
            return new ResponseEntity<>(NewCommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status("error")
                    .success(false)
                    .message(fieldError != null ? fieldError.getDefaultMessage() : "Unknown Error")
                    .data(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .error(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-up/referral-code")
    public ResponseEntity<NewCommonResponse<Boolean>> signUpWithReferralCode(
            @Valid @RequestBody SignUpWithReferralCodeDto signUpDto,
            BindingResult result, HttpServletRequest req) {
        try {
            if (result.hasErrors()) {
                throw new Exception("Error Creating new User");
            }
            Long userId = signUpService.signUpWithReferralCode(signUpDto);
            if (userId == null) {
                throw new Exception("Failed to create user with referral code");
            }
            try {
                walletService.create(userId);
            } catch (Exception e) {
                signUpService.deleteUserById(userId);
                throw new Exception("Failed to create wallet for user with referral code");
            }

            NewCommonResponse<Boolean> response = NewCommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.OK.value())
                    .status("success")
                    .data(true)
                    .message("User updated successfully")
                    .error(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            FieldError fieldError = result.getFieldError();
            return new ResponseEntity<>(NewCommonResponse.<Boolean>builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status("error")
                    .success(false)
                    .message(fieldError != null ? fieldError.getDefaultMessage() : "Unknown Error")
                    .data(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .error(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
