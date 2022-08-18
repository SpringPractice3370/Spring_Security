package com.example.jwtoauth11.service;

import com.example.jwtoauth11.config.security.jwt.JwtTokenProvider;
import com.example.jwtoauth11.domain.Member;
import com.example.jwtoauth11.dto.MemberLoginRequestDto;
import com.example.jwtoauth11.dto.MemberLoginResponseDto;
import com.example.jwtoauth11.dto.MemberRegisterRequestDto;
import com.example.jwtoauth11.dto.MemberRegisterResponseDto;
import com.example.jwtoauth11.exception.LoginFailureException;
import com.example.jwtoauth11.exception.MemberEmailAlreadyExistsException;
import com.example.jwtoauth11.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    public final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     */
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) {
        validateDuplicated(requestDto.getEmail());
        Member user = memberRepository.save(
                Member.builder()
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .build()
        );

        return new MemberRegisterResponseDto(user.getId(), user.getEmail());
    }

    /**
     * 로그인
     */
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {

        Member user = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(LoginFailureException::new);

        // 토큰 발급 구간
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
            throw new LoginFailureException();
        return new MemberLoginResponseDto(user.getId(), jwtTokenProvider.createToken(requestDto.getEmail()));

    }


    /**
     * 중복된 결과일 떄
     * @param email
     */
    public void validateDuplicated(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new MemberEmailAlreadyExistsException();
        }
    }
}
