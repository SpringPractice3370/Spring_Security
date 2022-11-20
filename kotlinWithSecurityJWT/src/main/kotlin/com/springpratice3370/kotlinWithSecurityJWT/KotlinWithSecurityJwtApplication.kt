package com.springpratice3370.kotlinWithSecurityJWT

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
class KotlinWithSecurityJwtApplication

fun main(args: Array<String>) {
	runApplication<KotlinWithSecurityJwtApplication>(*args)
}
