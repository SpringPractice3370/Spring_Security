package com.example.JwtWithOAuth2Test.api.repository.user;

import com.example.JwtWithOAuth2Test.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
