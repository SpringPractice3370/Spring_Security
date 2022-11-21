package com.springpratice3370.kotlinWithSecurityJWT.security

import com.springpratice3370.kotlinWithSecurityJWT.user.User
import com.springpratice3370.kotlinWithSecurityJWT.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val principal: User = userRepository.findByEmail(email) ?: throw RuntimeException()
        return PrincipalDetails(principal)
    }
}