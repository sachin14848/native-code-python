package com.authentication.Authentication.auth.dto.auth;

import com.authentication.Authentication.auth.dto.TokenType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.authentication.Authentication.auth.dto.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_token_expiry")
    private int accessTokenExpiry;
    @JsonProperty("token_type")
    private TokenType tokenType;
    @JsonProperty("user_name")
    private User user;

}