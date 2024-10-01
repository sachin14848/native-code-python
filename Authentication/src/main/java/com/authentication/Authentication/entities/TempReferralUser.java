package com.authentication.Authentication.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TempReferralUser {

    private Long userId;
    private String referralCode;
    private LocalDateTime useAt;

}
