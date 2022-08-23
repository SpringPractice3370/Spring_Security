package com.example.JwtWithOAuth2TestCredential.security.jwt;

import com.example.JwtWithOAuth2TestCredential.domain.Member;
import com.example.JwtWithOAuth2TestCredential.domain.RefreshToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenFactory {

    private JwtProperties properties;

    // jwt 헤더를 생성. 토큰 타입과 서명 알고리즘 종류를 명시
    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    // 엑세스 토큰 획득. 토큰 만료기간 및 공통 값은 properties 에서 확인
    public String getAccessToken(Member member) {
        return createAccessToken(member);
    }

    // 엑세스 토큰, 리프레시 토큰 생성
    public JwtTokenDto createToken(Member member) {
        String accessToken = createAccessToken(member);
        String refreshToken = createRefreshToken();

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 엑세스 토큰 생성
    private String createAccessToken(Member member) {
        Map<String, Object> header =createHeader();
        Instant now = Instant.now();

        Map<String, Object> payload = Map.of(
                "email", member.getEmail(),
                "user", member.getId(),
                "role", member.getRole());

        return Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .setIssuer(properties.getIssuer())
                .setExpiration(Date.from(now.plus((properties.getAccessTokenExpirationTime()), ChronoUnit.MINUTES)))
                .addClaims(payload)
                .signWith(Keys.hmacShaKeyFor(properties.getAccessTokenSigningKey().getBytes()))
                .compact();
    }
    // 리프레시 토큰 생성
    public String createRefreshToken() {
        Map<String, Object> header =createHeader();
        Instant now = Instant.now();

        return Jwts.builder()
                .setExpiration(Date.from(now.plus((properties.getRefreshTokenExpirationTime()), ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(properties.getRefreshTokenSigningKey().getBytes()))
                .compact();
    }

    // 엑세스 토큰 재발급
    public String createAccessToken(RefreshToken refreshToken) {
        Instant now = Instant.now();
        Map<String, Object> payload = Map.of(
                "email", refreshToken.getMemberEmail(),
                "user", refreshToken.getMemberId(),
                "role", refreshToken.getRole());

        return Jwts.builder()
                .setSubject(String.valueOf(refreshToken.getId()))
                .setIssuer(properties.getIssuer())
                .setExpiration(Date.from(now.plus((properties.getAccessTokenExpirationTime()), ChronoUnit.MINUTES)))
                .addClaims(payload)
                .signWith(Keys.hmacShaKeyFor(properties.getAccessTokenSigningKey().getBytes()))
                .compact();
    }
}