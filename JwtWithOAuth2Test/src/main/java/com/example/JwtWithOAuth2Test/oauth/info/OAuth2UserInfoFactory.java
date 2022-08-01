package com.example.JwtWithOAuth2Test.oauth.info;

import com.example.JwtWithOAuth2Test.oauth.entity.ProviderType;
import com.example.JwtWithOAuth2Test.oauth.info.impl.GoogleOAuth2Info;
import com.example.JwtWithOAuth2Test.oauth.info.impl.KakaoOAuth2Info;
import com.example.JwtWithOAuth2Test.oauth.info.impl.NaverOAuth2Info;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE:
                return new GoogleOAuth2Info(attributes);
            case NAVER:
                return new NaverOAuth2Info(attributes);
            case KAKAO:
                return new KakaoOAuth2Info(attributes);
            default:
                throw new IllegalArgumentException("Invalid Provider Type");
        }
    }
}
