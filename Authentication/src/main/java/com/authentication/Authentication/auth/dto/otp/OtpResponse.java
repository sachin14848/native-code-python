package com.authentication.Authentication.auth.dto.otp;

import lombok.*;

//@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpResponse {

    private String token;

}
