package com.example.securityBasic.controller;

import com.example.securityBasic.config.auth.PrincipleDetails;
import com.example.securityBasic.model.User;
import com.example.securityBasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    @ResponseBody
    public String testLogin(
            Authentication authentication, // 1번 방법 // DI(의존성 주입)
            @AuthenticationPrincipal PrincipleDetails userDetails) {  // 2번 방법
        System.out.println("/test/login ==============");
        PrincipleDetails principleDetails = (PrincipleDetails) authentication.getPrincipal(); // 1번
        System.out.println("authentication principleDetails.getUser() = " + principleDetails.getUser()); // 1번

        System.out.println("userDetails = " + userDetails.getUser()); // 2번
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String testOAuthLogin(
            Authentication authentication, // 1번
            @AuthenticationPrincipal OAuth2User oauth)  { // 2번
        System.out.println("/test/pauth/login ==============");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); // 1번
        System.out.println("authentication = " + oAuth2User.getAttributes()); // 1번

        System.out.println("oauth.getAttributes() = " + oauth.getAttributes()); // 2번

        return "OAuth 세션 정보 확인하기";
    }


    @GetMapping({"", "/"})
    public String index() {

        // 머스테치 기본 폴더 src/main/resource/
        // viewResolver 설정 : templates (prefix), .mustache(suffix)
        return "index"; // src/main/resource/templates/index.mustache
    }

    // OAuth 로그인을 해도 PrincipalDetails
    // 일반 로그인을 해도 PrincipalDetails
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipleDetails principleDetails) {
        System.out.println("principleDetails.getUser() = " + principleDetails.getUser());
        return "user";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    // SecurityConfig 파일 생성 후 작동 안함.
    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println("user = " + user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 함수가 시작되기전 실행
    // @PostAuthrize // 함수가 끝난 뒤 실행
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }
}
