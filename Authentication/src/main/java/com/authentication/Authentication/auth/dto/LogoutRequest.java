package com.authentication.Authentication.auth.dto;

import lombok.Data;

@Data
public class LogoutRequest {

    private String accessToken;
    private String refreshToken;

}
