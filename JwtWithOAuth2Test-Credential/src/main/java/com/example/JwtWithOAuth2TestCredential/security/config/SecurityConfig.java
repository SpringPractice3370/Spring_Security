package com.example.JwtWithOAuth2TestCredential.security.config;

import com.example.JwtWithOAuth2TestCredential.security.oauth.filter.OAuth2AccessTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2AccessTokenAuthenticationFilter oAuth2AccessTokenAuthenticationFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login/oauth2/*").permitAll() //로그인 화면 접근 가능
                .antMatchers("/").permitAll() //메인 화면 접근 가능
                .anyRequest().hasRole("USER");

        http.addFilterBefore(oAuth2AccessTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
