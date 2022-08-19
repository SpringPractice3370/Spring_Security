package com.example.jwtoauth11.config.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 발급받은 토큰을 기반으로 이를 처리해주는 필터
 *
 * JwtTokenProvider를 주입받아 헤더에서 토큰을 추출한다.
 * 토큰이 존재하는지 확인하고 존재한다면 만료시간이 지나지 않았는지 확인한다.
 * 성공했다면, 인증 객체를 받아오고 SecurityContextHolder에 저장하여 인증을 할 수 있도록 한다.
 * 그리고 doFilter 메소드를 통해 다음 필터로 넘어가 실제 AuthenticationFilter에서 이미 인증되어 있는 객체를 통해 인증이 되게 된다.
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateTokenExceptExpiration(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
