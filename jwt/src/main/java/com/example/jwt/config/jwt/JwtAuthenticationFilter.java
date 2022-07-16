package com.example.jwt.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송하면 (POST)
// UsernamePasswordAuthenticationFilter 가 동작을 함
// 근데 .formLogin().disable()를 해서 동작을 안함
// SecurityConfig 에서  .addFilter(new JwtAuthenticationFilter(authenticationManager())) 하면 됨

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도 중");

        // 정상이면
        // 1. username, password 받아서
        // 2. 정상인지 로그인 시도를 해봄.
        // authenticationManager 로 로그인 시도를 하면! PrincipalDetailsService 가 호출이 됨 -> loadUserByUsername() 함수 실행

        // 3. PrincipalDetails 를 세션에 담고 -> 담는 이유 : 권한 관리를 위해서
        // 4. JWT 토큰을 만들어서 응답해주면 됨
        return super.attemptAuthentication(request, response);
    }
}
