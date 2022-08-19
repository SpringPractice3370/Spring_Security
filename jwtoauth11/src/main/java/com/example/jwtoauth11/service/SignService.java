package com.example.jwtoauth11.service;

import com.example.jwtoauth11.config.security.jwt.JwtTokenProvider;
import com.example.jwtoauth11.domain.Member;
import com.example.jwtoauth11.dto.MemberLoginRequestDto;
import com.example.jwtoauth11.dto.MemberLoginResponseDto;
import com.example.jwtoauth11.dto.MemberRegisterRequestDto;
import com.example.jwtoauth11.dto.MemberRegisterResponseDto;
import com.example.jwtoauth11.dto.token.TokenRequestDto;
import com.example.jwtoauth11.dto.token.TokenResponseDto;
import com.example.jwtoauth11.exception.InvalidRefreshTokenException;
import com.example.jwtoauth11.exception.LoginFailureException;
import com.example.jwtoauth11.exception.MemberEmailAlreadyExistsException;
import com.example.jwtoauth11.exception.MemberNotFoundException;
import com.example.jwtoauth11.repository.MemberRepository;
import com.nimbusds.oauth2.sdk.TokenRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignService {

    public final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     */
    @Transactional
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) {
        validateDuplicated(requestDto.getEmail());
        Member user = memberRepository.save(
                Member.builder()
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .provider(null)
                        .build()
        );

        return MemberRegisterResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    /**
     * 로그인
     */
    @Transactional
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {

        Member user = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(LoginFailureException::new);

        // 토큰 발급 구간
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
            throw new LoginFailureException();

        // 리프레시 토큰 생성
        user.updateRefreshToken(jwtTokenProvider.createRefreshToken());
        return new MemberLoginResponseDto(user.getId(), jwtTokenProvider.createToken(requestDto.getEmail()), user.getRefreshToken());

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

    /**
     * 토큰 재발행
     * Dto로부터 Refresh Token을 꺼내 만료 기간이 지났는지 확인한다.
     * 지났다면, Exception을 발생시켜 다시 로그인을 진행할 수 있도록
     *
     *  findMemberByToken메소드를 활용해 파라미터로 입력받은 Access Token에 대한 회원 정보를 찾아온다.
     * 이 메소드는 유효한 토큰이라면 AccessToken으로부터 Email 정보를 받아와 DB에 저장된 회원을 찾고 해당 회원의 실제 Refresh Token을 받아온다.
     * 그리고 파라미터로 입력받은 Refresh Token과 실제 DB에 저장된 Refresh Token을 비교하여 검증한다.
     * 일치한다면, 올바른 Refresh Token으로 새로 Access Token과 Refresh Token을 발급해 이를 반환한다.
     */
    @Transactional
    public TokenResponseDto reIssue(TokenRequestDto requestDto) {
        if (!jwtTokenProvider.validateTokenExceptExpiration(requestDto.getRefreshToken())) {
            throw new InvalidRefreshTokenException();
        }

        Member member = findMemberByToken(requestDto);

        if (!member.getRefreshToken().equals(requestDto.getRefreshToken())) {
            throw new InvalidRefreshTokenException();
        }

        String accessToken = jwtTokenProvider.createToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        member.updateRefreshToken(refreshToken);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    public Member findMemberByToken(TokenRequestDto requestDto) {
        Authentication auth = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        return memberRepository.findByEmail(username).orElseThrow(MemberNotFoundException::new);
    }
}
