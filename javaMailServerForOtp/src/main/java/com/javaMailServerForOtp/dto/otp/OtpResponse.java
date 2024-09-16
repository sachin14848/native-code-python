package com.javaMailServerForOtp.dto.otp;

import com.javaMailServerForOtp.dto.CommonResponse;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse extends CommonResponse {

    private String token;

}
