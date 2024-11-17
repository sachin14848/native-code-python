package com.authentication.Authentication.auth.dto.auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String otp;

}
