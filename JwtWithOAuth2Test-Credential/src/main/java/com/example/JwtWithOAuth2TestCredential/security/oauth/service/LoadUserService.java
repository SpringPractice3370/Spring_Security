package com.example.JwtWithOAuth2TestCredential.security.oauth.service;

import com.example.JwtWithOAuth2TestCredential.domain.SocialType;
import com.example.JwtWithOAuth2TestCredential.security.oauth.authentication.AccessTokenSocialTypeToken;
import com.example.JwtWithOAuth2TestCredential.security.oauth.authentication.OAuth2UserDetails;
import com.example.JwtWithOAuth2TestCredential.security.oauth.service.strategy.GoogleLoadStrategy;
import com.example.JwtWithOAuth2TestCredential.security.oauth.service.strategy.KakaoLoadStrategy;
import com.example.JwtWithOAuth2TestCredential.security.oauth.service.strategy.NaverLoadStrategy;
import com.example.JwtWithOAuth2TestCredential.security.oauth.service.strategy.SocialLoadStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoadUserService {

    private final RestTemplate restTemplate = new RestTemplate();

    private SocialLoadStrategy socialLoadStrategy;//추상 클래스, 로그인을 진행하는 사이트레 따라 달라짐


    public OAuth2UserDetails getOAuth2UserDetails(AccessTokenSocialTypeToken authentication)  {

        SocialType socialType = authentication.getSocialType();

        setSocialLoadStrategy(socialType);//SocialLoadStrategy 설정

        Map<String, Object> socialPk = socialLoadStrategy.getSocialPk(authentication.getAccessToken());//PK 가져오기
        String email = socialPk.get("email").toString();
        String socialId = socialPk.get("id").toString();

        return OAuth2UserDetails.builder() //PK와 SocialType을 통해 회원 생성
                .socialId(socialId)
                .socialType(socialType)
                .email(email)
                .build();
    }

    private void setSocialLoadStrategy(SocialType socialType) {
        this.socialLoadStrategy = switch (socialType){

            case KAKAO -> new KakaoLoadStrategy();
            case GOOGLE ->  new GoogleLoadStrategy();
            case NAVER ->  new NaverLoadStrategy();
            default -> throw new IllegalArgumentException("지원하지 않는 로그인 형식입니다");
        };
    }


}