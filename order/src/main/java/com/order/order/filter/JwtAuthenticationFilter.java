package com.order.order.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.order.dto.ErrorResponse;
import com.order.order.entity.UserInfo;
import com.order.order.utils.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder accessTokenDecoder;
    private final JwtHelper jwtTokenUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            log.info("[JwtAccessTokenFilter:doFilterInternal]Filtering the Http Request:{}", request.getRequestURI());
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(TokenType.Bearer.name())) {
                throw new JwtException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);
            final Jwt jwtToken = accessTokenDecoder.decode(token);
            final String userName = jwtTokenUtils.getUserName(jwtToken);
            if (!userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("[JwtAccessTokenFilter:doFilterInternal]User found: {}", userName);
                UserInfo userDetails = jwtTokenUtils.userDetails(userName);
                log.info("[JwtAccessTokenFilter:doFilterInternal]User details: {}", userDetails.getUserName());
                if (jwtTokenUtils.isTokenValid(jwtToken, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken createToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            getAuthorities(userDetails)
                    );
                    createToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(createToken);
                    SecurityContextHolder.setContext(securityContext);
                } else {
                    throw new JwtException("Invalid or Expired JWT Access Token");
                }
            } else {
                throw new JwtException("User not found");
            }
            filterChain.doFilter(request, response);
        }catch (BadJwtException ex) {
            handleException(response, "Invalid JWT Token", "Token signature does not match locally computed signature");
        } catch (JwtException ex) {
            handleException(response, "Invalid or Expiry JWT Access Token", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            handleException(response, "Token expired", ex.getMessage());
        } catch (Exception ex) {
            handleException(response, "Internal Server Error", ex.getMessage());
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserInfo userInfo) {
        return Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    private void handleException(HttpServletResponse response, String error, String message) {
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
}
