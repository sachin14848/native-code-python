package com.authentication.Authentication.auth.repo.auth;

import com.authentication.Authentication.auth.entities.auth.otp.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtpAndId(String otp, Long id);
}
