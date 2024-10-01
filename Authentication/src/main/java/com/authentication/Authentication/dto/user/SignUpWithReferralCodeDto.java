package com.authentication.Authentication.dto.user;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpWithReferralCodeDto {


    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "User Phone No. Required")
    @NotNull(message = "User Phone No. Required")
    @Pattern(regexp = "^\\+91\\d{10}$", message = "Phone number must start with +91 and be followed by 10 digits")
    private String phoneNo;

    @Size(min = 8, max = 8, message = "Referral code must be exactly 8 characters")
    private String referralCode;

    @NotNull(message = "Accepting Terms and Conditions is required")
    @AssertTrue(message = "You must accept the Terms and Conditions")
    private Boolean acceptedTermsAndConditions;

}
