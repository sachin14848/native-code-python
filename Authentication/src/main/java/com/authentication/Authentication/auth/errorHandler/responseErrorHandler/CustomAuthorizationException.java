package com.authentication.Authentication.auth.errorHandler.responseErrorHandler;

public class CustomAuthorizationException extends RuntimeException {

    public CustomAuthorizationException(String message) {
        super(message);
    }
}
