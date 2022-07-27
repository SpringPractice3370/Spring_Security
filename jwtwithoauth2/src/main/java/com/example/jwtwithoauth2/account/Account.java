package com.example.jwtwithoauth2.account;

import com.example.jwtwithoauth2.auth.oauth2.model.Role;
import com.example.jwtwithoauth2.auth.oauth2.model.OAuth2Provider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_oauth2_id")
    private Long oauth2Id;

    @Column(name = "account_email")
    private String email;

    @Column(name = "account_nickname")
    private String nickname;

    @Column(name = "account_oauth2_provider")
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    @Column(name = "account_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "account_is_first") // 가입 이후 추가정보 입력 여부
    private boolean isFirst;

    @Builder(access = AccessLevel.PRIVATE)

    public Account(Long id, Long oauth2Id, String email, String nickname, OAuth2Provider provider, Role role, boolean isFirst) {
        this.id = id;
        this.oauth2Id = oauth2Id;
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
        this.role = role;
        this.isFirst = isFirst;
    }

    /**
     * 신규 사용자 계정 엔티티를 생성해서 반환한다.
     * 신규 사용자를 추가할 때 사용한다.
     *
     * @param oauth2Id OAuth2 로그인 후 받은 해당 OAuth2 계정 식별자
     * @param email    OAuth2 로그인 후 받은 해당 OAuth2 프로필 정보상의 이메일
     * @param nickname OAuth2 로그인 후 받은 해당 OAuth2 프로필 정보상의 닉네임
     * @param provider OAuth2 사업자
     * @return account - 신규 계정 엔티티, 권한은 일반 사용자 권한, 추가 정보 입력 여부 미입력으로 자동 설정
     */

    public static Account createAccount(Long oauth2Id, String email, String nickname, OAuth2Provider provider) {
        return Account.builder()
                .oauth2Id(oauth2Id)
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .role(Role.ROLE_USER)
                .isFirst(true)
                .build();
    }


    /**
     * 기존 계정 정보를 바탕으로 계정 엔티티를 생성한다.<br>
     * 신규 계정 추가가 아닌 단순 계정 엔티티로의 변화가 필요할 때 사용한다.<br>
     * 이 메소드로 생성한 엔티티는 영속상태가 아니기 때문에 다양한 논리적 문제를 발생시킬 수 있다.<br>
     * Account 에 대한 DB 조작이 필요한 경우 DB 에서 읽어온 Account 엔티티로 처리해야 한다.
     * @param id 계정 식별자
     * @param email 계정 이메일
     * @param nickname 계정 닉네임
     * @param role 계정 권한 정보
     * @return account 객체
     */

    public static Account convertAccount(Long id, String email, String nickname, Role role) {
        return Account.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .role(role)
                .build();

    }

}
