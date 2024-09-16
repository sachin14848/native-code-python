package com.mailSending.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse extends CommonResponse {

    private String token;

}
