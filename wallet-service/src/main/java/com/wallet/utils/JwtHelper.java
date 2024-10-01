package com.wallet.utils;



import com.wallet.entities.UserInfo;
import com.wallet.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtHelper {

    private final UserRepo userInfoRepo;

    public String getUserName(Jwt jwtToken) {
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(Jwt jwtToken, UserInfo userDetails) {
        final String userName = getUserName(jwtToken);
        boolean isTokenExpired = getIfTokenIsExpired(jwtToken);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getEmailId());
        return !isTokenExpired && isTokenUserSameAsDatabase;
    }

    private boolean getIfTokenIsExpired(Jwt jwtToken) {
        return Objects.requireNonNull(jwtToken.getExpiresAt()).isBefore(Instant.now());
    }


    public UserInfo userDetails(String emailId) {
        return userInfoRepo
                .findByEmailId(emailId)
                .orElseThrow(() -> new UsernameNotFoundException("User Email: " + emailId + " does not exist"));
    }
}
