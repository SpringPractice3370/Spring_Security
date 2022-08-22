package com.example.JwtWithOAuth2TestCredential.repository;

import com.example.JwtWithOAuth2TestCredential.domain.Member;
import com.example.JwtWithOAuth2TestCredential.domain.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType , String socialId);
}
