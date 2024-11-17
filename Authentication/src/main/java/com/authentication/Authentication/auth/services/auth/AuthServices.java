package com.authentication.Authentication.auth.services.auth;

import com.authentication.Authentication.auth.dto.auth.AuthResponseDto;
import com.authentication.Authentication.auth.dto.TokenType;
import com.authentication.Authentication.auth.dto.user.User;
import com.authentication.Authentication.auth.entities.auth.RefreshTokenEntity;
import com.authentication.Authentication.auth.entities.auth.UserInfoEntity;
import com.authentication.Authentication.auth.jwtAuth.JwtTokenGenerator;
import com.authentication.Authentication.auth.repo.auth.RefreshTokenRepo;
import com.authentication.Authentication.auth.repo.auth.UserInfoRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServices {

    private final UserInfoRepo userInfoRepo;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepo refreshTokenRepo;

    public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response, HttpServletRequest request) throws Exception {
//        try {/
        var userInfoEntity = userInfoRepo.findByEmailId(authentication.getName())
                .orElseThrow(() -> new Exception("USER NOT FOUND!"));

        String token = jwtTokenGenerator.generateAccessToken(authentication);
        String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
        saveUserRefreshToken(userInfoEntity, refreshToken, request);
        creatRefreshTokenCookie(response, request, refreshToken);
        int dd = userInfoRepo.updatePasswordAndOtpExpiryData(userInfoEntity.getEmailId(), "");
        return AuthResponseDto.builder()
                .accessToken(token)
                .accessTokenExpiry(60)
                .tokenType(TokenType.Bearer)
                .user(User.builder()
                        .userId(userInfoEntity.getId())
                        .username(userInfoEntity.getUserName())
                        .email(userInfoEntity.getEmailId())
                        .role(userInfoEntity.getRoles())
                        .build())
                .build();

    }


    public Object getAccessTokenUsingRefreshToken(String authHeader) {

        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(authHeader)
                .filter(token -> !token.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh token revoked"));
        UserInfoEntity userInoEntity = refreshTokenEntity.getUser();
        Authentication authentication = createAuthenticationObject(userInoEntity);
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .tokenType(TokenType.Bearer)
                .build();
    }


    private static Authentication createAuthenticationObject(UserInfoEntity userInoEntity) {
        try {
            String userName = userInoEntity.getEmailId();
            String password = userInoEntity.getPassword();
            String roles = userInoEntity.getRoles();

            String[] roleArray = roles.split(",");
            GrantedAuthority[] authorities = Arrays.stream(roleArray)
                    .map(role -> (GrantedAuthority) role::trim)
                    .toArray(GrantedAuthority[]::new);
            return new UsernamePasswordAuthenticationToken(userName, password, Arrays.asList(authorities));
        } catch (Exception e) {
            throw new RuntimeException("Error creating authentication object: " + e.getMessage());
        }
    }

    private void saveUserRefreshToken(UserInfoEntity userInoEntity, String refreshToken, HttpServletRequest request) {
        var refreshTokenEntity = RefreshTokenEntity.builder()
                .domainName(request.getServerName())
                .refreshToken(refreshToken)
                .user(userInoEntity)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    private void creatRefreshTokenCookie(HttpServletResponse response, HttpServletRequest request, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60);
        refreshTokenCookie.setDomain(request.getServerName()); // Set the correct domain
        response.addCookie(refreshTokenCookie);
    }
}
