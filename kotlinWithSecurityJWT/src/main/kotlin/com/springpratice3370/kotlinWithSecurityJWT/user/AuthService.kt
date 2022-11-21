package com.springpratice3370.kotlinWithSecurityJWT.user

import com.springpratice3370.kotlinWithSecurityJWT.jwt.JwtProvider
import com.springpratice3370.kotlinWithSecurityJWT.jwt.TokenDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val clock: Clock

) {
    fun test(loginRequest: LoginRequest): TokenDto {
        val authenticationToken: UsernamePasswordAuthenticationToken? = loginRequest.toAuthentication()
        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        return jwtProvider.generateTokenDto(authentication)
    }

    fun singUp(loginRequest: LoginRequest): User {

        val email = loginRequest.email
        val now = clock.instant()
        return userRepository.save(
            User(
                email = email,
                hashedPassword = passwordEncoder.encode(loginRequest.password),
                phoneNumber = "324134",
                nickname = "ASfsadfs",
                createdAt = now,
                updatedAt = now,
                role = Role.ROLE_USER
            )
        )
    }

}