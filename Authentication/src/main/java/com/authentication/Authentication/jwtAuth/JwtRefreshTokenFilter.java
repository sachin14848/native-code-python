package com.authentication.Authentication.jwtAuth;

import com.authentication.Authentication.utiles.RefreshToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.authentication.Authentication.dto.ErrorResponse;
import com.authentication.Authentication.repo.RefreshTokenRepo;
import com.authentication.Authentication.utiles.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

    private JwtDecoder refreshTokenDecoder;
    private RefreshTokenRepo refreshTokenRepo;
    private JwtTokenUtils jwtTokenUtils;

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
                if (cookie.getName().equals(RefreshToken.refresh_token.name())) {
                    refreshToken = cookie.getValue();
                }
            }
            log.info("Refresh token : {}", refreshToken);
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
                logger.info("User &&&&&&&&&&&&&&&&&&++++++++++ token: " + userDetails.getAuthorities());
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authorities);
                updatedAuthorities.add(new SimpleGrantedAuthority("SCOPE_REFRESH_TOKEN"));
                if (jwtTokenUtils.isTokenValid(jwtRefreshToken, userDetails) && isRefreshTokenValidInDatabase) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken createdToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            updatedAuthorities
                    );
                    createdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(createdToken);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
        } catch (InvalidBearerTokenException ex) {
            handleException(response, "Invalid Token", ex.getMessage());
        } catch (BadJwtException ex) {
            handleException(response, "Error To Decode The Token", ex.getMessage());
        } catch (JwtException ex) {
            handleException(response, "Invalid or Expiry JWT Refresh Token", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            handleException(response, "Token expired", ex.getMessage());
        } catch (Exception ex) {
            handleException(response, "Internal Server Error", ex.getMessage());
        } finally {
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
