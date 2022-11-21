package com.springpratice3370.kotlinWithSecurityJWT.security

import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {
    val currentAccountEmail: String
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null || authentication.name == null) {
                throw RuntimeException()
            }
            return authentication.name
        }
}