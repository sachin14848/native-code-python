package com.javaMailServerForOtp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCommonResponse<T> {

    private boolean success;
    private String category;
    private String timestamp;
    private String path;
    private String requestId;
    private int statusCode;
    private String status;
    private String message;
    private String error;
    private T data;
}
