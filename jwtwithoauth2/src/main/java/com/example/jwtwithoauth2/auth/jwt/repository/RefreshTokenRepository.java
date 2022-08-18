package com.example.jwtwithoauth2.auth.jwt.repository;


import com.example.jwtwithoauth2.auth.jwt.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByRefreshToken(String refreshToken);
}
