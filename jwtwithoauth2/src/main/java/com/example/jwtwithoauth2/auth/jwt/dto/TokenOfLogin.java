package com.example.jwtwithoauth2.auth.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenOfLogin {

    private String refreshToken;
    private String accessToken;

}
