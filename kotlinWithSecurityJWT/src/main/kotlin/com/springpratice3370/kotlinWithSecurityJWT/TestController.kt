package com.springpratice3370.kotlinWithSecurityJWT

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/api/hello")
    fun hello(): String {
        return "hello"
    }
}