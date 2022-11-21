package com.springpratice3370.kotlinWithSecurityJWT.security

import com.springpratice3370.kotlinWithSecurityJWT.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetails (private val user: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        authorities.add(GrantedAuthority { user.status.toString() })
        return authorities
    }

    override fun getPassword(): String {
        return user.hashedPassword
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
