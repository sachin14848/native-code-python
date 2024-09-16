package com.javaMailServerForOtp.errorHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        try {
            log.info("Access handle denied: " + request.getRequestURI());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access denied: " + accessDeniedException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
