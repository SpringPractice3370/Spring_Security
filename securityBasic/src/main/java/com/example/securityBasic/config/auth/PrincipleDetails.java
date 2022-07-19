package com.example.securityBasic.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아 채서 로그인을 진행시킴.
// 로그인을 진행이 완료가 되면 session 을 만들어 줌. (시큐리티는 시큐리티만의 세션을 가지고 있음 공간은 동일, Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨.
// User 오브젝트의 타입은 => UserDetails 타입 객체

// Security Session => (들어갈 수 있는 객체는) Authentication => (Authentication 안에 유저 정보를 저장할 떈) UserDetails(PrincipleDetails)

import com.example.securityBasic.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipleDetails implements UserDetails {

    private User user; // 콤포지션

    public PrincipleDetails(User user) {
        this.user = user;
    }


    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 전환.
        // 현재 시간 - 로그인 시간 => 1년을 초과하면 return false;

        return true;
    }

}
