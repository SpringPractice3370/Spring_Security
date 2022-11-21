package com.springpratice3370.kotlinWithSecurityJWT.jwt

data class TokenDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long
);

