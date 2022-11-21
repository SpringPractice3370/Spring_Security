package com.springpratice3370.kotlinWithSecurityJWT.jwt

import com.springpratice3370.kotlinWithSecurityJWT.security.PrincipalDetailService
import com.springpratice3370.kotlinWithSecurityJWT.security.PrincipalDetails
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
class JwtProvider(
    private val principalDetailService: PrincipalDetailService,
) {
    @Value("\${jwt.secret-key}")
    var secretKey: String = "Asdfasdfadadsflk1273898192374128341023430819274810238;aksdjfalsdak;sdjf;sjdkfaksdjfak;sfasdf"
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    // 토큰생성
    fun generateTokenDto(authentication: Authentication): TokenDto {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        val now = Date().time
        val accessTokenExpiresIn = Date(now + TokenInfo.ACCESS_TOKEN_EXPIRE_TIME)

        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim(TokenInfo.AUTHORITIES_KEY, authorities)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken = Jwts.builder()
            .setExpiration(Date(now + TokenInfo.REFRESH_TOKEN_EXPIRE_TIME))
            .setSubject(authentication.name)
            .claim(TokenInfo.AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return TokenDto(
            grantType = TokenInfo.BEARER_TYPE,
            accessToken = accessToken,
            accessTokenExpiresIn = accessTokenExpiresIn.time,
            refreshToken = refreshToken
        )
    }

    fun validateToken(token: String?): Boolean {
        val claims2 = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            ?: return false
        return claims2.body.expiration.after(Date())
    }


    fun getAuthentication(token: String?): Authentication? {
        val claims: Claims = parseClaims(token)
        requireNotNull(claims[TokenInfo.AUTHORITIES_KEY]) { "권한 정보가 없는 토큰입니다." }

        val authorities: Collection<GrantedAuthority?> =
            Arrays.stream(claims[TokenInfo.AUTHORITIES_KEY].toString().split(",").toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }.collect(Collectors.toList())

        val principalDetails: PrincipalDetails = principalDetailService.loadUserByUsername(claims.subject) as PrincipalDetails
        return UsernamePasswordAuthenticationToken(principalDetails, "", authorities)
    }


    private fun parseClaims(token: String?): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}