package com.javaMailServerForOtp.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {

    @NotEmpty(message = "User name Required")
    @NotNull(message = "User name Required")
    private String username;

    @NotEmpty(message = "User Phone No. Required")
    @NotNull(message = "User Phone No. Required")
    @Pattern(regexp = "^\\+91\\d{10}$", message = "Phone number must start with +91 and be followed by 10 digits")
    private String phoneNumber;

}
