package com.authentication.Authentication.services;

import com.authentication.Authentication.dto.LogoutRequest;
import com.authentication.Authentication.entities.LogoutAccessToken;
import com.authentication.Authentication.entities.RefreshTokenEntity;
import com.authentication.Authentication.repo.LogoutAccessTokenRepo;
import com.authentication.Authentication.repo.RefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
