package com.example.JwtWithOAuth2TestCredential.security.jwt.filter;

import com.example.JwtWithOAuth2TestCredential.security.jwt.JwtPreAuthenticationToken;
import com.example.JwtWithOAuth2TestCredential.security.jwt.exception.AuthenticationHeaderNotFountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";


    public JwtAuthenticationFilter(String request) {
        super(request);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("jwt auth 필터 작동");

        if (request.getRequestURI().equals("/api/refresh")) {
            return null;
        }
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        // 인증 헤더가 없을 경우 예외
        if (Objects.isNull(authorizationHeader)) {
            throw new AuthenticationHeaderNotFountException("인증 헤더가 존재하지 않습니다.");
        }

        // Bearer 접두어 제거
        String token = authorizationHeader.substring(BEARER_PREFIX.length());
        log.info("Bearer 접두어 제거 됨 {}", token);

        JwtPreAuthenticationToken preAuthenticationToken = new JwtPreAuthenticationToken(token);
        return this.getAuthenticationManager().authenticate(preAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}