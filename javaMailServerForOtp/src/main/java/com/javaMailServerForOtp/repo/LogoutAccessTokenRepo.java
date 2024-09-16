package com.javaMailServerForOtp.repo;

import com.javaMailServerForOtp.entities.LogoutAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogoutAccessTokenRepo extends JpaRepository<LogoutAccessToken, Long> {
    Optional<LogoutAccessToken> findByToken(String accessToken);
}
