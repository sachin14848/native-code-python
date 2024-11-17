package com.authentication.Authentication.auth.services.auth;

import com.authentication.Authentication.auth.dto.user.SignUpDto;
import com.authentication.Authentication.auth.dto.user.SignUpWithReferralCodeDto;
import com.authentication.Authentication.auth.entities.user.ReferralCode;
import com.authentication.Authentication.auth.entities.user.ReferralUsage;
import com.authentication.Authentication.auth.entities.user.SignUp;
import com.authentication.Authentication.auth.repo.user.ReferralCodeRepo;
import com.authentication.Authentication.auth.repo.user.ReferralUsageRepo;
import com.authentication.Authentication.auth.repo.user.SignUpRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpService {

    private final SignUpRepo signUpRepo;
    private final ReferralCodeService referralCodeService;
    private final ReferralUsageRepo referralUsageRepo;
    private final ReferralCodeRepo referralCodeRepo;

    public SignUpService(SignUpRepo signUpRepo,
                         ReferralCodeService referralCodeService,
                         ReferralUsageRepo referralUsageRepo,
                         ReferralCodeRepo referralCodeRepo) {
        this.signUpRepo = signUpRepo;
        this.referralCodeService = referralCodeService;
        this.referralUsageRepo = referralUsageRepo;
        this.referralCodeRepo = referralCodeRepo;
    }


    @Transactional
    public Long signUpWithReferralCode(SignUpWithReferralCodeDto signUpDto) {
        if (signUpRepo.findByEmailId(signUpDto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
        // Check if the phone number already exists
        if (signUpRepo.findByPhone(signUpDto.getPhoneNo()).isPresent()) {
            throw new IllegalStateException("Phone number already exists");
        }
        SignUp newUser = new SignUp();
        newUser.setUserName(signUpDto.getUsername());
        newUser.setEmailId(signUpDto.getEmail());
        newUser.setPhone(signUpDto.getPhoneNo());
        newUser.setAcceptedTermsAndConditions(signUpDto.getAcceptedTermsAndConditions());
        newUser.setPassword("kjdfksdjfklsjdflksjdkfljdsklfjskldjflksdjflksd");
        newUser.setRoles("ROLE_USER");
        ReferralUsage referralCodeUse = referralCodeService.useReferralCode(signUpDto.getReferralCode(), newUser);
        if (referralCodeUse != null) {
            ReferralCode referralCode = referralCodeService.generateReferralCodeForUser(newUser);
            newUser.setReferralCode(referralCode);
            // Save the user to the database
            signUpRepo.save(newUser);
            referralCodeUse.setUsedBy(newUser);
            referralUsageRepo.save(referralCodeUse);
            return newUser.getId();
        }
        return null;
    }

    @Transactional
    public void deleteUserById(Long userId) {
        Optional<SignUp> user = signUpRepo.findById(userId);
        if (user.isPresent()) {
            referralUsageRepo.deleteByUsedBy(user.get());
            referralCodeRepo.deleteByUserId(userId);
            signUpRepo.deleteById(userId);
        }
    }

    @Transactional
    public void signUpWithoutReferralCode(SignUpDto signUpDto) {
        // Check if the email already exists
        if (signUpRepo.findByEmailId(signUpDto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }

        // Check if the phone number already exists
        if (signUpRepo.findByPhone(signUpDto.getPhoneNo()).isPresent()) {
            throw new IllegalStateException("Phone number already exists");
        }

        // If email and phone number are unique, save the new user
        SignUp newUser = new SignUp();
        newUser.setUserName(signUpDto.getUsername());
        newUser.setEmailId(signUpDto.getEmail());
        newUser.setPhone(signUpDto.getPhoneNo());
        newUser.setAcceptedTermsAndConditions(signUpDto.getAcceptedTermsAndConditions());
        newUser.setPassword("kjdfksdjfklsjdflksjdkfljdsklfjskldjflksdjflksd");
        newUser.setRoles("ROLE_USER");

        ReferralCode referralCode = referralCodeService.generateReferralCodeForUser(newUser);
        newUser.setReferralCode(referralCode);
        // Save the user to the database
        signUpRepo.save(newUser);
//        walletService.create(newUser.getId());
    }
}
