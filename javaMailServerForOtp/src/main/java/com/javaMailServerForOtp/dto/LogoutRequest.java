package com.javaMailServerForOtp.dto;

import lombok.Data;

@Data
public class LogoutRequest {

    private String accessToken;
    private String refreshToken;

}
