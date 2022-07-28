package com.example.jwtwithoauth2.auth.jwt.token;

import com.example.jwtwithoauth2.auth.oauth2.model.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(name = "account_email")
    private String accountEmail;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "role")
    private Role role;

    @Column(name = "client_id")
    private String clientIp;

    @Builder
    public RefreshToken(String accountEmail, Long accountId, String refreshToken, Role role) {
        this.accountEmail = accountEmail;
        this.accountId = accountId;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public RefreshToken createRefreshToken(String accountEmail, Long accountId, String refreshToken, Role role) {
        return RefreshToken.builder()
                .accountEmail(accountEmail)
                .accountId(accountId)
                .refreshToken(refreshToken)
                .role(role)
                .build();
    }
}
