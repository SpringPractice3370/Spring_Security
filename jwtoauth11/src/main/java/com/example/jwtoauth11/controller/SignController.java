package com.example.jwtoauth11.controller;

import com.example.jwtoauth11.dto.MemberLoginRequestDto;
import com.example.jwtoauth11.dto.MemberLoginResponseDto;
import com.example.jwtoauth11.dto.MemberRegisterRequestDto;
import com.example.jwtoauth11.dto.MemberRegisterResponseDto;
import com.example.jwtoauth11.dto.result.SingleResult;
import com.example.jwtoauth11.dto.token.TokenRequestDto;
import com.example.jwtoauth11.dto.token.TokenResponseDto;
import com.example.jwtoauth11.service.ResponseService;
import com.example.jwtoauth11.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;
    private final ResponseService responseService;

    @PostMapping("/register")
    public SingleResult<MemberRegisterResponseDto> register(@RequestBody MemberRegisterRequestDto requestDto) {
        MemberRegisterResponseDto responseDto = signService.registerMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @PostMapping("/login")
    public SingleResult<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @PostMapping("/reissue")
    public SingleResult<TokenResponseDto> reIssue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenResponseDto responseDto = signService.reIssue(tokenRequestDto);
        return responseService.getSingleResult(responseDto);
    }
}