package com.authentication.Authentication.auth.repo.user;

import com.authentication.Authentication.auth.entities.user.ReferralCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralCodeRepo extends JpaRepository<ReferralCode, Long> {
    Optional<ReferralCode> findByCode(String code);
    boolean existsByCode(String code);

    void deleteByUserId(Long userId);
}
