package com.example.jwtwithoauth2.auth.jwt.service;

import com.example.jwtwithoauth2.account.Account;
import com.example.jwtwithoauth2.auth.jwt.repository.RefreshTokenRepository;
import com.example.jwtwithoauth2.auth.jwt.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(Account loggedInUser, String refreshToken) {
        // refresh token Entity
        RefreshToken refreshTokenEntity = new RefreshToken();

        refreshTokenRepository.save(refreshTokenEntity.createRefreshToken(
                loggedInUser.getEmail(),
                loggedInUser.getId(),
                refreshToken,
                loggedInUser.getRole()
        ));

    }
    public void renewalRefreshToken(String refreshToken){

    }
}
