package com.example.JwtWithOAuth2TestCredential.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String issuer;

    private String accessTokenSigningKey;
    private String refreshTokenSigningKey;

    private Long accessTokenExpirationTime;
    private Long refreshTokenExpirationTime;
}
