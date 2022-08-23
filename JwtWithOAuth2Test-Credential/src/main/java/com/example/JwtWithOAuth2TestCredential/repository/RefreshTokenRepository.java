package com.example.JwtWithOAuth2TestCredential.repository;

import com.example.JwtWithOAuth2TestCredential.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByRefreshToken(String refreshToken);
}

