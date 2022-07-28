package com.example.jwtwithoauth2.auth.jwt.util;

import com.example.jwtwithoauth2.account.Account;
import com.example.jwtwithoauth2.auth.jwt.JwtProperties;
import com.example.jwtwithoauth2.auth.jwt.token.TokenType;
import com.example.jwtwithoauth2.auth.oauth2.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

/**
 * <h1>JwtDecoder</h1>
 * <p>
 *     JWT 토큰을 디코드 하고 민료, 변조 등의 예외가 없는지 확인하는 클래스
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtProperties properties;

    /**
     * JWT 토큰을 검증하고 검증 성공시 계정 엔티티 또는 null 을 반환한다. 검증 실패시 반드시 예외를 발생시킨다.
     *
     * @param token 검증 대상 토큰 값
     * @return account - access token 일 경우 변환한 엔티티, null - refresh token 일 경우
     * @throws ExpiredJwtException   토큰 만료시 발생
     * @throws SignatureException    토큰 서명이 틀린 경우 발생
     * @throws MissingClaimException 토큰에 특정 클레임이 없는 경우 발생
     * @throws MalformedJwtException 토큰이 유효한 형식이 아닌 경우 발생
     */

    public Account verify(String token, TokenType tokenType) throws ExpiredJwtException, SignatureException, MissingClaimException, MalformedJwtException {

        String key = properties.getAccessTokenSigningKey();

        if (tokenType == TokenType.REFRESH_TOKEN) {
            key = properties.getRefreshTokenSigningKey();
        }


        Jws<Claims> jwt = Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor("kGO2WGNVgLVHVhz5M1Y8nQuT7mH69JHlGqSk5X91237M=".getBytes()))
//                .requireIssuer("oauthwithjwt")
                .setSigningKey(key.getBytes())
                .requireIssuer(properties.getIssuer())
                .build()
                .parseClaimsJws(token);

        if (tokenType == TokenType.ACCESS_TOKEN) {
            Claims jwtBody = jwt.getBody();
            Long id = Long.valueOf(jwtBody.getSubject());
            String email = (String) jwtBody.get("email");
            String nickname = (String) jwtBody.get("user");
            Role role = Role.valueOf((String) jwtBody.get("role"));

            return Account.convertAccount(id, email, nickname, role);
        }

        // refresh 경우
        return null;
    }

}
