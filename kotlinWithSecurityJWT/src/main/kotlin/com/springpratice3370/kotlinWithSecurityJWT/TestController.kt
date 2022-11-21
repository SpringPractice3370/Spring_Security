package com.springpratice3370.kotlinWithSecurityJWT

import com.springpratice3370.kotlinWithSecurityJWT.jwt.TokenDto
import com.springpratice3370.kotlinWithSecurityJWT.user.AuthService
import com.springpratice3370.kotlinWithSecurityJWT.user.LoginRequest
import com.springpratice3370.kotlinWithSecurityJWT.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val authServie: AuthService

) {
    @GetMapping("/api/hello")
    fun hello(): String {
        return "hello"
    }

    @PostMapping("/api/login")
    fun login(@RequestBody loginRequest: LoginRequest): TokenDto {
        return authServie.test(loginRequest)
    }

    @PostMapping("/api/sign")
    fun singUp(@RequestBody loginRequest: LoginRequest): User {
        return authServie.singUp(loginRequest)
    }

    @GetMapping("/me")
    fun getMe():User {
        return authServie.findMe()
    }

}