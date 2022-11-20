package com.springpratice3370.kotlinWithSecurityJWT.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
    ) : WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {

            http.csrf().disable()

            http.authorizeRequests()
                .antMatchers("/api/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        @Bean
        fun passwordEncoder() = BCryptPasswordEncoder()

}