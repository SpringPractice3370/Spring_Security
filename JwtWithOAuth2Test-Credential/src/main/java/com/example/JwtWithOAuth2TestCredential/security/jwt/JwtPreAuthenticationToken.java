package com.example.JwtWithOAuth2TestCredential.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtPreAuthenticationToken extends AbstractAuthenticationToken {

    private String token;

    public JwtPreAuthenticationToken(String token) {
        super(null); // 인증 전 객체이므로 권한 정보가 없음.
        this.setAuthenticated(false);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
