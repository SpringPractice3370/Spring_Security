package com.example.securityBasic.config.oauth;

import com.example.securityBasic.config.auth.PrincipleDetails;
import com.example.securityBasic.config.oauth.provider.*;
import com.example.securityBasic.model.User;
import com.example.securityBasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    // 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어짐
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken().getTokenValue());
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth 로 로그인 했는지 확인 가능.

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인을 완료 -> code 를 리턴 (OAuth-Client 라이브러리가 받음) -> AccessToken 요청
        // userRequest 정보 -> loadUser 함수 호출 (회원 프로필을 받아야 함) -> 구글로 부터 회원 프로필을 받음.
        System.out.println("getAttributes = " + oAuth2User.getAttributes());

        // 회원 가입 강제로 진행
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        else{
            System.out.println("구글과 페이스북, 네이버만 지원함");
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId; // google_115581661486985905711
        String password = bCryptPasswordEncoder.encode("gggg");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            System.out.println("OAuth2 로그인이 최초 입니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println("로그인을 이미 한 적이 있습니다. 자동 회원가입 되어 있습니다.");
        }

        return new PrincipleDetails(userEntity, oAuth2User.getAttributes());
    }
}
