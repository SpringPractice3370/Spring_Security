package com.example.jwtwithoauth2.auth.jwt.filter;

import com.example.jwtwithoauth2.auth.jwt.JwtProperties;
import com.example.jwtwithoauth2.auth.jwt.exception.JwtExpiredTokenException;
import com.example.jwtwithoauth2.auth.jwt.exception.JwtModulatedTokenException;
import com.example.jwtwithoauth2.auth.jwt.service.RefreshTokenService;
import com.example.jwtwithoauth2.auth.jwt.token.TokenType;
import com.example.jwtwithoauth2.auth.jwt.util.JwtDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter {

    @Autowired
    private final RefreshTokenService refreshTokenService;

    @Autowired
    private JwtProperties jwtProperties;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/api/refresh")) {
            // Request 토큰 추출
            ServletInputStream inputStream = request.getInputStream();
            Map<String, Object> map = objectMapper.readValue(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8), Map.class);
            String refreshToken = (String) map.get("refresh");


            try {
                getJwtDecoder().verify(refreshToken, TokenType.ACCESS_TOKEN);
            } catch (SignatureException | MalformedJwtException | MissingClaimException exception) {
                throw new JwtModulatedTokenException("변조된 JWT 토큰입니다");
            } catch (ExpiredJwtException ex) {
                throw new JwtExpiredTokenException("만료된 JWT 토큰입니다.");
            }

            refreshTokenService.renewalRefreshToken(refreshToken);

        }

        filterChain.doFilter(request, response);
    }

    public JwtDecoder getJwtDecoder() {
        return new JwtDecoder(this.jwtProperties);
    }
}
