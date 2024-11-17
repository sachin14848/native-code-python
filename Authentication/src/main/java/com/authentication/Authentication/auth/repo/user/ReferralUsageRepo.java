package com.authentication.Authentication.auth.repo.user;

import com.authentication.Authentication.auth.entities.user.ReferralCode;
import com.authentication.Authentication.auth.entities.user.ReferralUsage;
import com.authentication.Authentication.auth.entities.user.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralUsageRepo extends JpaRepository<ReferralUsage, Long> {
    List<ReferralUsage> findByReferralCode(ReferralCode referralCode);

    void deleteByUsedBy(SignUp user);
}
