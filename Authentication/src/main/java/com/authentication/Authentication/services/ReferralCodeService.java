package com.authentication.Authentication.services;

import com.authentication.Authentication.entities.user.ReferralCode;
import com.authentication.Authentication.entities.user.ReferralUsage;
import com.authentication.Authentication.entities.user.SignUp;
import com.authentication.Authentication.repo.user.ReferralCodeRepo;
import com.authentication.Authentication.repo.user.ReferralUsageRepo;
import com.authentication.Authentication.repo.user.SignUpRepo;
import com.authentication.Authentication.utiles.ReferralCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferralCodeService {

    private final SignUpRepo signUpRepo;
    private final ReferralUsageRepo referralUsageRepo;
    private final ReferralCodeRepo referralCodeRepo;
    private final RedisTemplate<String, ReferralUsage> redisTemplate;

    public ReferralCode generateReferralCodeForUser(SignUp user) {
        ReferralCode referralCode = new ReferralCode();
        referralCode.setCode(generateUniqueCode());
        referralCode.setUser(user);
        return referralCodeRepo.save(referralCode);
    }

    public ReferralUsage useReferralCode(String code, SignUp user) {
        Optional<ReferralCode> referralCodeOptional = referralCodeRepo.findByCode(code);
        if (referralCodeOptional.isPresent()) {
            ReferralCode referralCode = referralCodeOptional.get();

            // Check if the user is not using their own code
            if (!referralCode.getUser().getId().equals(user.getId())) {

                ReferralUsage referralUsage = new ReferralUsage();
                referralUsage.setReferralCode(referralCode);
                referralUsage.setUsedAt(LocalDateTime.now());

                rewardUsers(referralCode.getUser(), user);
                return referralUsage;
            }
        }else {
            throw new IllegalStateException("Referral code not found");
        }
        return null; // Invalid or user's own referral code
    }

    private void rewardUsers(SignUp referrer, SignUp referredUser) {
        // Logic to reward both users
        // Example: Give points or gifts to both referrer and the referred user
    }


    private String generateUniqueCode() {
        String code;
        do {
            code = ReferralCodeGenerator.generateReferralCode();
        } while (referralCodeRepo.existsByCode(code));  // Ensure unique referral code
        return code;
    }


}
