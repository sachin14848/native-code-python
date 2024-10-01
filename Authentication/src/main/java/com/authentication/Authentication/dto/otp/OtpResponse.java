package com.authentication.Authentication.dto.otp;

import com.authentication.Authentication.dto.CommonResponse;
import lombok.*;

//@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpResponse {

    private String token;

}
