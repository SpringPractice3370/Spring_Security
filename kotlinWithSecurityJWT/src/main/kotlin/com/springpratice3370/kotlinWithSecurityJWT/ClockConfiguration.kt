package com.springpratice3370.kotlinWithSecurityJWT

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class ClockConfiguration {

    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }
}
