package com.authentication.Authentication.repo.otp;

import com.authentication.Authentication.entities.otp.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtpAndId(String otp, Long id);
}
