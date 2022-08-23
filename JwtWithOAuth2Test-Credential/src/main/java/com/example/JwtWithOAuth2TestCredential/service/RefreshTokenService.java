package com.example.JwtWithOAuth2TestCredential.service;

import com.example.JwtWithOAuth2TestCredential.domain.Member;
import com.example.JwtWithOAuth2TestCredential.domain.RefreshToken;
import com.example.JwtWithOAuth2TestCredential.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // 리프레시 토큰 저장
    public void saveRefreshToken(Member loginMember, String refreshToken) {
        refreshTokenRepository.save(createRefreshToken(loginMember, refreshToken));
    }

    public void renewalRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    // 로그인 한 유저 리프레시 토큰 생성
    public RefreshToken createRefreshToken(Member loginMember, String refreshToken) {
        return RefreshToken.createRefreshToken(
                loginMember.getEmail(),
                loginMember.getId(),
                refreshToken,
                loginMember.getRole());
    }

    // 리프레시 토큰 찾기
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public void removeRefreshToken(Long id) {
        refreshTokenRepository.deleteById(id);
    }

}
