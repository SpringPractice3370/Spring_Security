package com.example.jwtwithoauth2.auth.common.config;


import com.example.jwtwithoauth2.auth.jwt.filter.JwtAuthenticationFilter;
import com.example.jwtwithoauth2.auth.jwt.handler.JwtAuthenticationFailureHandler;
import com.example.jwtwithoauth2.auth.jwt.provider.JwtAuthenticationProvider;
import com.example.jwtwithoauth2.auth.oauth2.handler.OAuth2LoginAuthenticationSuccessHandler;
import com.example.jwtwithoauth2.auth.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;

import javax.servlet.Filter;


/**
 * <h1>SecurityConfig</h1>
 * <p>Spring Security 관련 설정 클래스</p>
 * <p>이 클래스는 다음 내용에 관한 설정을 담당한다.</p>
 * <ul>
 *     <li>사용자 권한에 따른 URL 보안 설정</li>
 *     <li>security filter 설정 및 필터체인 등록</li>
 * </ul>
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // OAuth2 Beans
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2LoginAuthenticationSuccessHandler oAuth2LoginAuthenticationSuccessHandler;

    // jwt Beans
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    public Filter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter("/api/**");
        filter.setAuthenticationManager(super.authenticationManager());
        filter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
        return filter;
    }

    // authentication manager setting
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // OAuth2 Filter chain configuration
        http.oauth2Login()
                .successHandler(oAuth2LoginAuthenticationSuccessHandler)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        // JWT Authentication filter chain configuration
        http.addFilterBefore(jwtAuthenticationFilter(), OAuth2AuthorizationRequestRedirectFilter.class);

        // URL security
        http.authorizeRequests()
                .antMatchers("/api/a").access("hasRole('ROLE_ADMIN')");
    }
}
