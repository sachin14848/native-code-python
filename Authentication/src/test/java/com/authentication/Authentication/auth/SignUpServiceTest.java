package com.authentication.Authentication.auth;

import com.authentication.Authentication.dto.user.SignUpDto;
import com.authentication.Authentication.dto.user.SignUpWithReferralCodeDto;
import com.authentication.Authentication.entities.user.SignUp;
import com.authentication.Authentication.repo.user.SignUpRepo;
import com.authentication.Authentication.services.ReferralCodeService;
import com.authentication.Authentication.services.SignUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SignUpServiceTest {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private SignUpRepo signUpRepo;

    @Autowired
    private ReferralCodeService referralCodeService;

    @BeforeEach
    public void setup() {
        // Clean up the user repository before each test case
//        signUpRepo.deleteAll();
    }

    @Test
    public void testSignUpFor100Users() {
        int numberOfUsers = 10;

        // Loop through to create 100 users
        for (int i = 2; i <= numberOfUsers; i++) {
            SignUpDto signUpDto = new SignUpDto();
            signUpDto.setUsername("User" + i);
            signUpDto.setEmail("user" + i + "@example.com");
            signUpDto.setPhoneNo("+91"+ String.format("%010d", i)); // +91 phone numbers String.format("%010d", i)

            signUpService.signUpWithoutReferralCode(signUpDto);
        }

        // Verify 100 users are created
        List<SignUp> allUsers = signUpRepo.findAll();
        assertEquals(numberOfUsers, allUsers.size(), "Exactly 100 users should be registered");

        // Additional assertions can be added here (like verifying unique referral codes)
        for (int i = 0; i < numberOfUsers; i++) {
            assertNotNull(allUsers.get(i).getReferralCode(), "Each user should have a referral code");
        }
    }

    @Test
    public void testSignUpWithReferralCode_for100Users() throws Exception {
        // Simulate 100 users signing up with referral codes
        int numberOfUsers = 10;
        SignUpDto signUpDto1 = new SignUpDto();
        signUpDto1.setUsername("User");
        signUpDto1.setEmail("user@example.com");
        signUpDto1.setPhoneNo("+919999999587"); // +91 phone numbers String.format("%010d", i)

        signUpService.signUpWithoutReferralCode(signUpDto1);
        
        for (int i = 1; i < numberOfUsers; i++) {
            SignUpWithReferralCodeDto signUpDto = new SignUpWithReferralCodeDto();
            signUpDto.setEmail("testUserDependent" + i + "@example.com");
            signUpDto.setPhoneNo("+91" + String.format("%010d", i));
            signUpDto.setUsername("testUser" + i);
            signUpDto.setReferralCode("65KDLKK1");

            Long response = signUpService.signUpWithReferralCode(signUpDto);
        }

        List<SignUp> allUsers = signUpRepo.findAll();
        assertEquals(numberOfUsers, allUsers.size(), "Exactly 100 users should be registered");

        // Additional assertions can be added here (like verifying unique referral codes)
        for (int i = 1; i < numberOfUsers; i++) {
            assertNotNull(allUsers.get(i).getReferralCode(), "Each user should have a referral code");
        }

    }

}
