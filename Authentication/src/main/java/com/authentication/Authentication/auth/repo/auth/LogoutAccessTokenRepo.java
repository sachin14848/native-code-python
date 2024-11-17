package com.authentication.Authentication.auth.repo.auth;

import com.authentication.Authentication.auth.entities.auth.LogoutAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogoutAccessTokenRepo extends JpaRepository<LogoutAccessToken, Long> {
    Optional<LogoutAccessToken> findByToken(String accessToken);
}
