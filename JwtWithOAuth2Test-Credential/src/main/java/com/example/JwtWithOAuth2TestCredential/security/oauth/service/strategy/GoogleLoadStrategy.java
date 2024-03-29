package com.example.JwtWithOAuth2TestCredential.security.oauth.service.strategy;

import com.example.JwtWithOAuth2TestCredential.domain.SocialType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
public class GoogleLoadStrategy extends SocialLoadStrategy{



    protected Map<String, Object> sendRequestToSocialSite(HttpEntity request){
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(SocialType.GOOGLE.getUserInfoUrl(),
                    SocialType.GOOGLE.getMethod(),
                    request,
                    RESPONSE_TYPE);

            Map<String, Object> response2 = (Map<String, Object>) response.getBody().get("email");//구글은 email를 PK로 사용
            System.out.println("response2name = " +   response2.get("name").toString());
            System.out.println("response2email = " +   response2.get("email").toString());
            return response2;


        } catch (Exception e) {
            log.error("AccessToken을 사용하여 GOOGLE 유저정보를 받아오던 중 예외가 발생했습니다 {}" ,e.getMessage() );
            throw e;
        }
    }
}
