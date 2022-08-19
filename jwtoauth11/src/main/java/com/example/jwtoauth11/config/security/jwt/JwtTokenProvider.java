package com.example.jwtoauth11.config.security.jwt;

import com.example.jwtoauth11.config.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Setter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 30; // 30분
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; // 7일


    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * AccessToken 생성
     */
    public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    /**
     * refreshToken 생성
     * 오직 재발행의 목적으로만 사용하기에 sub 불필요
     */
    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    /**
     * 토큰으로 인증 객체(Authentication)을 얻기 위한 메소드이다.
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getMemberEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 이메일을 얻기 위해 실제로 토큰을 디코딩하는 부분이다.
     * 지정한 Secret Key를 통해 서명된 JWT를 해석하여 Subject를 끌고와 리턴하여 이를 통해 인증 객체를 끌고올 수 있다.
     * @param token
     * @return
     */
    public String getMemberEmail(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    /**
     * 토큰이 만료되었는 지를 확인해주는 메소드이다.
     * token을 디코딩하여 만료시간을 끌고와 현재시간과 비교해 확인해준다.
     * @param token
     * @return
     */
    public boolean validateTokenExceptExpiration(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * 토큰은 HTTP Header에 저장되어 계속적으로 이용되어진다.
     * 토큰을 사용하기 위해 실제로 Header에서 꺼내오는 메소드이다.
     * @param req
     * @return
     */
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

}
