package com.example.JwtWithOAuth2TestCredential.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationHeaderNotFountException extends AuthenticationException {

    public AuthenticationHeaderNotFountException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationHeaderNotFountException(String msg) {
        super(msg);
    }


}