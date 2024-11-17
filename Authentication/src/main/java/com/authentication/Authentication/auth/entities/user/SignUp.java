package com.authentication.Authentication.auth.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class SignUp {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(nullable = false, name = "EMAIL_ID", unique = true)
    private String emailId;

    @Column(nullable = false, name = "PASSWORD")
//    @Convert(converter = AttributesConvertor.class)
    private String password;

    @Column(name = "PHONE_NO", unique = true)
//    @Convert(converter = AttributesConvertor.class)
    private String phone;

    @Column(name = "OTP")
    private String otp;

    @Column(nullable = false, name = "ROLES")
    private String roles;

    @Column(name = "OTP_EXPIRATION_TIME")
    private Date otpExpirationTime;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ReferralCode referralCode;

    // Users who used this referral code
    @OneToMany(mappedBy = "usedBy", cascade = CascadeType.ALL)
    private List<ReferralUsage> referralUsages;

    @Column(name = "ACCEPTED_TERMS_AND_CONDITIONS", nullable = false)
    private Boolean acceptedTermsAndConditions;

}