package com.springpratice3370.kotlinWithSecurityJWT.user

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginRequest(
    var email: String,
    val password: String
){
    fun toAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, password)
    }

}

