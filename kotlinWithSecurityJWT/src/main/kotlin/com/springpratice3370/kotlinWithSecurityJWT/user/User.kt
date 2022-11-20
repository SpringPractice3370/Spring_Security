package com.springpratice3370.kotlinWithSecurityJWT.user

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long = 0,

    var email: String,

    var hashedPassword: String,

    var phoneNumber: String? = null,

    var nickname: String,

    // TODO: 성능 이슈가 생기면 Attribute Converter 도입
    @Enumerated(EnumType.STRING)
    var status: Status = Status.ACTIVE,

    @Enumerated(EnumType.STRING)
    var role: Role,

    var description: String? = null,

    @Column(name = "fcm_registration_token")
    var pushToken: String? = null,

    var pushNotificationEnabledAt: Instant? = null,

    @CreationTimestamp
    var createdAt: Instant,

    @UpdateTimestamp
    var updatedAt: Instant,

    var withdrawnAt: Instant? = null,
) {
    enum class Status(description: String) {
        ACTIVE("활성 유저"),
        WITHDRAWN("탈퇴 유저"),
    }
    fun isPushNotificationEnable() = pushNotificationEnabledAt != null
    fun isWithdrawn() = status == Status.WITHDRAWN
    fun getNickName() = if (status == Status.WITHDRAWN) "탈퇴한 유저" else nickname
}
