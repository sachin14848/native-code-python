package com.javaMailServerForOtp.errorHandler.responseErrorHandler;

public class CustomAuthorizationException extends RuntimeException {

    public CustomAuthorizationException(String message) {
        super(message);
    }
}
