package com.example.JwtWithOAuth2TestCredential.domain;


import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @GeneratedValue
    @Id
    private Long id;

    private String socialId;
    private String email;
    private String nickname;
    private boolean isFirst;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    //    private String profileImageUrl;
//
//    private String introduction;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id, String socialId, String email, boolean isFirst, String nickname, SocialType socialType, Role role) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.isFirst = isFirst;
        this.socialType = socialType;
        this.role = role;
    }

    // 신규 사용자
    public static Member createMember(String socialId, String email, String nickname, SocialType socialType) {
        return Member.builder()
                .socialId(socialId)
                .email(email)
                .nickname(nickname)
                .socialType(socialType)
                .role(Role.USER)
                .isFirst(true)
                .build();
    }

    // 기존 계정 정보
    public static Member convertMember(Long id, String email, String nickname, Role role) {
        return Member.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }
}
