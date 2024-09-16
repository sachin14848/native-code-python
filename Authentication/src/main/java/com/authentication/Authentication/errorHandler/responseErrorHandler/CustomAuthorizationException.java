package com.authentication.Authentication.errorHandler.responseErrorHandler;

public class CustomAuthorizationException extends RuntimeException {

    public CustomAuthorizationException(String message) {
        super(message);
    }
}
