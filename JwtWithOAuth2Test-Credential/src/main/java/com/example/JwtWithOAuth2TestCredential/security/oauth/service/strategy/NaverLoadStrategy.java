package com.example.JwtWithOAuth2TestCredential.security.oauth.service.strategy;

import com.example.JwtWithOAuth2TestCredential.domain.SocialType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public class NaverLoadStrategy extends SocialLoadStrategy{


    protected Map<String, Object> sendRequestToSocialSite(HttpEntity request){
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(SocialType.NAVER.getUserInfoUrl(),//
                    SocialType.NAVER.getMethod(),
                    request,
                    RESPONSE_TYPE);


            Map<String, Object> response2 = (Map<String, Object>) response.getBody().get("response");

            System.out.println("response2name = " +   response2.get("name").toString());
            System.out.println("response2id = " +   response2.get("id").toString());
            System.out.println("response2email = " +   response2.get("email").toString());

            return response2;


        } catch (Exception e) {
            log.error("AccessToken을 사용하여 NAVER 유저정보를 받아오던 중 예외가 발생했습니다 {}" ,e.getMessage() );
            throw e;
        }
    }
}
