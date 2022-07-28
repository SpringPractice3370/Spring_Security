package com.example.jwtwithoauth2.auth.jwt.provider;

import com.example.jwtwithoauth2.account.Account;
import com.example.jwtwithoauth2.auth.jwt.exception.JwtExpiredTokenException;
import com.example.jwtwithoauth2.auth.jwt.exception.JwtModulatedTokenException;
import com.example.jwtwithoauth2.auth.jwt.token.JwtPostAuthenticationToken;
import com.example.jwtwithoauth2.auth.jwt.token.TokenType;
import com.example.jwtwithoauth2.auth.jwt.util.JwtDecoder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.stereotype.Component;

import java.security.SignatureException;

/**
 * <h1>JwtAuthenticationProvider</h1>
 * <p>
 *     JWT 토큰 문자열을 검증하고 계정 엔티티로 바꾸는 작업을 관리한다.
 * </p>
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();
        try {
            Account verifiedAccount = jwtDecoder.verify(token, TokenType.ACCESS_TOKEN);
            return new JwtPostAuthenticationToken(verifiedAccount);
        } catch (SignatureException | MalformedJwtException | MissingClaimException ex) {
            throw new JwtModulatedTokenException("변조된 JWT 토큰입니다.");
        } catch (ExpiredJwtException ex) {
            throw new JwtExpiredTokenException("만료된 JWT 토큰입니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
