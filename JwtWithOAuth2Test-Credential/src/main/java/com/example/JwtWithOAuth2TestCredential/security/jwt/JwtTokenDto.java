package com.example.JwtWithOAuth2TestCredential.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;
}

