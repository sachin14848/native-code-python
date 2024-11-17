package com.authentication.Authentication.auth.services.auth;

import com.authentication.Authentication.auth.entities.auth.LogoutAccessToken;
import com.authentication.Authentication.auth.entities.auth.RefreshTokenEntity;
import com.authentication.Authentication.auth.repo.auth.LogoutAccessTokenRepo;
import com.authentication.Authentication.auth.repo.auth.RefreshTokenRepo;
import com.authentication.Authentication.utiles.RefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    @Transactional
    public boolean processLogout(HttpServletRequest logoutRequest, HttpServletResponse response) {

        String accessToken = logoutRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken == null) {
            return false;
        }
        blacklistToken(accessToken);
        Cookie[] cookies = logoutRequest.getCookies();
        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(RefreshToken.refresh_token.name())) {
                refreshToken = cookie.getValue();
            }
        }
        if (refreshToken == null) {
            return false;
        }
        removeRefreshTokenFromDatabase(refreshToken);
        clearCookie(RefreshToken.refresh_token.name(), logoutRequest, response);
        return true;
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

    private void clearCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/auth"); // Ensure this matches the path where the cookie was set
        cookie.setHttpOnly(true); // Ensure HttpOnly is set (as when the cookie was created)
        cookie.setMaxAge(0); // Max age 0 deletes the cookie
        cookie.setSecure(true); // Ensure Secure is set if it was set when creating the cookie
        cookie.setDomain(request.getServerName()); // Set the correct domain
        response.addCookie(cookie); // Add cookie to the response
    }

}
