package com.authentication.Authentication.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "referral_code")
public class ReferralCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SignUp user;  // The user who owns the referral code

    @OneToMany(mappedBy = "referralCode", cascade = CascadeType.ALL)
    private List<ReferralUsage> usages;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
