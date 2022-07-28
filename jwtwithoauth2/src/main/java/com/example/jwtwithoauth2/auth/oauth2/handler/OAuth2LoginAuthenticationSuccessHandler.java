package com.example.jwtwithoauth2.auth.oauth2.handler;

import com.example.jwtwithoauth2.account.Account;
import com.example.jwtwithoauth2.auth.jwt.util.JwtTokenFactory;
import com.example.jwtwithoauth2.auth.oauth2.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenFactory jwtTokenFactory;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // 로그인 된 사용자 계정
        Account loggedInUser = oAuth2User.getAccount();
        log.info("로그인된 사용자 계정 {}",loggedInUser);

        // 추가정보 기입 여부
        boolean first = loggedInUser.isFirst();
        log.info("추가정보 기입 여부 first {}", first);

        // 액세스 토큰
        String accessToken = jwtTokenFactory.createAccessToken(loggedInUser);
        log.info("액세스 토큰 accessToken {}", accessToken);

        String uri = UriComponentsBuilder
                .fromUriString(redirectUrl)
                .queryParam("access-token", accessToken)
                .queryParam("is-first", first)
                .toUriString();

        response.sendRedirect(uri);

    }
}
