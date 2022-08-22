package com.example.JwtWithOAuth2TestCredential.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @GeneratedValue
    @Id
    private Long id;

    private String memberEmail;
    private Long memberId;
    private String refreshToken;

    private Role role;

    @Builder
    public RefreshToken(String memberEmail, Long memberId, String refreshToken, Role role) {
        this.memberEmail = memberEmail;
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public static RefreshToken createRefreshToken(String memberEmail, Long memberId, String refreshToken, Role role) {
        return RefreshToken.builder()
                .memberEmail(memberEmail)
                .memberId(memberId)
                .refreshToken(refreshToken)
                .role(role)
                .build();
    }
}