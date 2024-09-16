package com.javaMailServerForOtp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MailDto {

    @NotEmpty(message = "Recipient Mail is required!")
    @Email(message = "Invalid email format")
    private String emailTo;

}
