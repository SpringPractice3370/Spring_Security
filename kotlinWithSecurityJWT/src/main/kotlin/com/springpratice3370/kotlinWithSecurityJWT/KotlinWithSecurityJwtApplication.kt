package com.springpratice3370.kotlinWithSecurityJWT

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
	ClockConfiguration::class
)
class KotlinWithSecurityJwtApplication
fun main(args: Array<String>) {

	runApplication<KotlinWithSecurityJwtApplication>(*args)
}
