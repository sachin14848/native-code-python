package com.javaMailServerForOtp.services;

import com.javaMailServerForOtp.dto.LogoutRequest;
import com.javaMailServerForOtp.entities.LogoutAccessToken;
import com.javaMailServerForOtp.entities.RefreshTokenEntity;
import com.javaMailServerForOtp.repo.LogoutAccessTokenRepo;
import com.javaMailServerForOtp.repo.RefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogoutService {
    private final RefreshTokenRepo refreshTokenRepo;
    private final LogoutAccessTokenRepo logoutAccessTokenRepo;

    public static Set<String> blacklistedTokens = new HashSet<>();

    public void processLogout(LogoutRequest logoutRequest) {
        blacklistToken(logoutRequest.getAccessToken());
        removeRefreshTokenFromDatabase(logoutRequest.getRefreshToken());
    }

    private void removeRefreshTokenFromDatabase(String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
        refreshTokenEntity.setRevoked(true);
        refreshTokenEntity.setRefreshToken("");
        refreshTokenRepo.save(refreshTokenEntity);
    }

    public void blacklistToken(String token) {
        LogoutAccessToken blacklistAccessToken = new LogoutAccessToken();
        blacklistAccessToken.setToken(token);
        blacklistAccessToken.setRevoked(true);
        logoutAccessTokenRepo.save(blacklistAccessToken);
    }

}
