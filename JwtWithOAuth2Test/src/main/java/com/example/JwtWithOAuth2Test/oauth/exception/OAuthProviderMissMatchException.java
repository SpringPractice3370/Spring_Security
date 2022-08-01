package com.example.JwtWithOAuth2Test.oauth.exception;

public class OAuthProviderMissMatchException extends RuntimeException{

    public OAuthProviderMissMatchException(String message) {
        super(message);
    }
}
