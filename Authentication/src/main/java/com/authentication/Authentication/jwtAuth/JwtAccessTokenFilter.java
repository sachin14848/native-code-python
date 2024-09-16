package com.authentication.Authentication.jwtAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.authentication.Authentication.dto.ErrorResponse;
import com.authentication.Authentication.dto.TokenType;
import com.authentication.Authentication.repo.LogoutAccessTokenRepo;
import com.authentication.Authentication.utiles.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Slf4j
public class JwtAccessTokenFilter extends OncePerRequestFilter {

    private JwtDecoder accessTokenDecoder;
    private JwtTokenUtils jwtTokenUtils;
    private LogoutAccessTokenRepo logoutAccessTokenRepo;

    public JwtAccessTokenFilter(@Qualifier("accessTokenDecoder") JwtDecoder accessTokenDecoder, JwtTokenUtils jwtTokenUtils, LogoutAccessTokenRepo logout) {
        this.accessTokenDecoder = accessTokenDecoder;
        this.jwtTokenUtils = jwtTokenUtils;
        this.logoutAccessTokenRepo = logout;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(TokenType.Bearer.name())) {
                throw new JwtException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);
            if (isTokenBlacklisted(token)) {
                throw new JwtException("You Are logged out. Please log in again.");
            }
            final Jwt jwtToken = accessTokenDecoder.decode(token);
            final String userName = jwtTokenUtils.getUserName(jwtToken);
            if (!userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtTokenUtils.userDetails(userName);
                if (jwtTokenUtils.isTokenValid(jwtToken, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken createToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    createToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(createToken);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            handleException(response, "Invalid JWT Token", e.getMessage());
        } catch (JwtException ex) {
            handleException(response, "Invalid or Expiry JWT Access Token", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            handleException(response, "Token expired", ex.getMessage());
        } catch (Exception ex) {
            handleException(response, "Internal Server Error", ex.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, String error, String message) throws IOException {
        try {
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // or any other appropriate status
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Access Token failed", error, message);
            String json = new ObjectMapper().writeValueAsString(errorResponse);

            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
        } catch (IOException ex) {
            log.error("[JwtAccessTokenFilter:handleException] Error while writing response: {}", ex.getMessage());
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return logoutAccessTokenRepo.findByToken(token).isPresent();
    }

}
