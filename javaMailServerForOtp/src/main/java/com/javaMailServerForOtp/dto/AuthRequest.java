package com.javaMailServerForOtp.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String otp;

}
