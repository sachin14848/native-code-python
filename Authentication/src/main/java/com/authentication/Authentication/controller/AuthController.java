package com.authentication.Authentication.controller;

import com.authentication.Authentication.dto.*;
import com.authentication.Authentication.dto.otp.OtpResponse;
import com.authentication.Authentication.services.AuthServices;
import com.authentication.Authentication.services.LogoutService;
import com.authentication.Authentication.services.MailService;
import com.authentication.Authentication.utiles.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.auth.HttpAuthenticator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthServices authService;
    private final MailService mailService;
    private final LogoutService logoutService;

    @PostMapping("/sign-in")
    public ResponseEntity<NewCommonResponse<AuthResponseDto>> authenticateUser(Authentication authentication,
                                                                               HttpServletResponse response,
                                                                               @RequestBody AuthRequest request, HttpServletRequest req) {
        log.info("User has been authenticated: {}", request.getUsername());
        try {
            if (mailService.isValidateOtp(request.getOtp(), request.getUsername())) {
                AuthResponseDto responseDto = authService.getJwtTokensAfterAuthentication(authentication, response, req);

                return ResponseEntity.ok(NewCommonResponse.<AuthResponseDto>builder()
                        .status("success")
                        .success(true)
                        .message("Otp sent successfully")
                        .category("Authentication")
                        .statusCode(HttpStatus.OK.value())
                        .data(responseDto)
                        .path(req.getRequestURI())
                        .requestId(UUID.randomUUID().toString())
                        .error(null)
                        .build());
            } else {
                throw new Exception("Invalid OTP");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(NewCommonResponse.<AuthResponseDto>builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .status("error")
                    .success(false)
                    .message("Error to Authentication")
                    .error(e.getMessage())
                    .data(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> getAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, HttpServletRequest req) {
        try {
            log.info("getAccessToken");
            final Cookie[] cookies = req.getCookies();
            String refreshToken = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(RefreshToken.refresh_token.name())) {
                    refreshToken = cookie.getValue();
                }
            }
            if (refreshToken == null) {
                throw new Exception("Refresh token is required in Cookie header");
            }
            final Object data = authService.getAccessTokenUsingRefreshToken(refreshToken);

            return ResponseEntity.ok(NewCommonResponse.<Object>builder()
                    .status("success")
                    .success(true)
                    .message("Otp sent successfully")
                    .category("Authentication")
                    .statusCode(HttpStatus.OK.value())
                    .data(data)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .error(null)
                    .build());
        } catch (Exception e) {
            return new ResponseEntity<>(NewCommonResponse.<String>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status("error")
                    .success(false)
                    .message("Error creating authorization")
                    .error("Error")
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .build(),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/otp")
    public ResponseEntity<NewCommonResponse<?>> sendOtp(@Valid @RequestBody MailDto mailDto, HttpServletRequest req) {
        try {
            String token = mailService.generateToken(mailDto.getEmailTo()); // Assuming generateOtp method is defined in AuthServices class
            String data = mailService.sendEmail(mailDto, token);
            if (data == null) {
                throw new Exception("Could not send email");
            }


            return ResponseEntity.ok(NewCommonResponse.<OtpResponse>builder()
                    .status("success")
                    .success(true)
                    .message("Otp sent successfully")
                    .statusCode(HttpStatus.OK.value())
                    .data(OtpResponse.builder().token(data).build())
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .error(null)
                    .build());
        } catch (Exception e) {
            return new ResponseEntity<>(NewCommonResponse.<String>builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status("error")
                    .success(false)
                    .message("Error To send Mail")
                    .category("Authentication")
                    .error(e.getMessage())
                    .data(null)
                    .path(req.getRequestURI())
                    .requestId(UUID.randomUUID().toString())
                    .build(),
                    HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // The part of the header value after "Bearer "

        new SecurityContextLogoutHandler().logout(request, response, authentication);
        boolean sucess = logoutService.processLogout(request, response); // Assuming logout method is defined in LogoutService class
        return ResponseEntity.ok("{\"message\":\"Logout successful\"}");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toMap(key -> key, key -> "Error"));
        return ResponseEntity.badRequest().body(errors);
    }

}