package com.authentication.Authentication.dto.otp;

import com.authentication.Authentication.dto.CommonResponse;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse extends CommonResponse {

    private String token;

}
