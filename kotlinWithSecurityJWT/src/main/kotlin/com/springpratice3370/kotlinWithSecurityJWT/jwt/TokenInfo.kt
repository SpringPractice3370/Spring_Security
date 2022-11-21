package com.springpratice3370.kotlinWithSecurityJWT.jwt

class TokenInfo {
    companion object {
        const val AUTHORITIES_KEY = "AUTH"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_TYPE = "Bearer"
        const val START_TOKEN_LOCATION = 7
        const val ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L //30min
        const val REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L // 1week
    }
}