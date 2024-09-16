package com.authentication.Authentication.jwtAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.authentication.Authentication.dto.ErrorResponse;
import com.authentication.Authentication.repo.RefreshTokenRepo;
import com.authentication.Authentication.utiles.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private JwtDecoder refreshTokenDecoder;
    private RefreshTokenRepo refreshTokenRepo;
    private JwtTokenUtils jwtTokenUtils;
    private boolean isValidRefreshToken;

    public JwtRefreshTokenFilter(@Qualifier("refreshTokenDecoder") JwtDecoder refreshTokenDecoder, RefreshTokenRepo refreshTokenRepo, JwtTokenUtils jwtTokenUtils) {
        this.refreshTokenDecoder = refreshTokenDecoder;
        this.refreshTokenRepo = refreshTokenRepo;
        this.jwtTokenUtils = jwtTokenUtils;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("[JwtRefreshTokenFilter:doFilterInternal]Filtering the Http Request:{}", request.getRequestURI());
            final Cookie[] cookies = request.getCookies();
            String refreshToken = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    refreshToken = cookie.getValue();
                }
            }
            log.info("Refresh token : {}", refreshToken);
//            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (refreshToken == null) {
                throw new JwtException("Refresh token is required in Cookie header");
            }
            final Jwt jwtRefreshToken = refreshTokenDecoder.decode(refreshToken);

            final String userName = jwtTokenUtils.getUserName(jwtRefreshToken);
            System.out.println(userName);

            if (!userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

                var isRefreshTokenValidInDatabase = refreshTokenRepo.findByRefreshToken(refreshToken)
                        .map(refresh -> !refresh.isRevoked())
                        .orElseThrow(() -> new JwtException("Refresh token not found or revoked"));
                UserDetails userDetails = jwtTokenUtils.userDetails(userName);
                if (jwtTokenUtils.isTokenValid(jwtRefreshToken, userDetails) && isRefreshTokenValidInDatabase) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken createdToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    createdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(createdToken);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
//            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            handleException(response, "Invalid or Expiry JWT Refresh Token", ex.getMessage());
        } catch (Exception ex) {
            handleException(response, "Internal Server Error", ex.getMessage());
        }finally {
            filterChain.doFilter(request, response);
        }

    }

    private void handleException(HttpServletResponse response, String error, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // or any other appropriate status
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Authentication failed", error, message);
        String json = new ObjectMapper().writeValueAsString(errorResponse);

        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
