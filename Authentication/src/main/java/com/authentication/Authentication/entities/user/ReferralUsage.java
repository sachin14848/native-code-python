package com.authentication.Authentication.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "referral_usage")
public class ReferralUsage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "referral_code_id")
    private ReferralCode referralCode;

    @ManyToOne
    @JoinColumn(name = "used_by_user_id")
    private SignUp usedBy;  // User who used the referral code

    private LocalDateTime usedAt;

}
