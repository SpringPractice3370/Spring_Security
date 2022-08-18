package com.example.JwtWithOAuth2Test.oauth.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("Failed to generate Token");
    }

    private TokenValidFailedException(String messgae) {
        super(messgae);
    }
}
