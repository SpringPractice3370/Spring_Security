package com.example.jwtwithoauth2.auth.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {

    public JwtExpiredTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }
}