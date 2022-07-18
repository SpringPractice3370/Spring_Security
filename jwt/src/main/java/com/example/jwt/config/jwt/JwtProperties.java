package com.example.jwt.config.jwt;

public interface JwtProperties {
    String SECRET = "cos"; // 우리 서버만 알고 있는 비밀 값
    int EXPIRATION_TIME = 60000 * 1;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
