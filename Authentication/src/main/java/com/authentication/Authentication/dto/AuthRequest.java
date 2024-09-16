package com.authentication.Authentication.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String otp;

}
