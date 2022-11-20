package com.springpratice3370.kotlinWithSecurityJWT.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmailOrPhoneNumberOrNickname(email: String, phoneNumber: String, nickname: String): List<User>

    fun findByEmailOrNickname(email: String, nickname: String): List<User>

    fun findByEmail(email: String): User?

    fun findByPhoneNumber(phoneNumber: String): User?

    fun findByNickname(nickname: String): User?

    fun findByIdIn(ids: Set<Long>): List<User>
}
