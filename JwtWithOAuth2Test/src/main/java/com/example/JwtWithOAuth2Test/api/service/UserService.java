package com.example.JwtWithOAuth2Test.api.service;

import com.example.JwtWithOAuth2Test.api.entity.user.User;
import com.example.JwtWithOAuth2Test.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
