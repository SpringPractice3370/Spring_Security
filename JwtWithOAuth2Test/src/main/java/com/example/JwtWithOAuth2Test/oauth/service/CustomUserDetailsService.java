package com.example.JwtWithOAuth2Test.oauth.service;

import com.example.JwtWithOAuth2Test.api.entity.user.User;
import com.example.JwtWithOAuth2Test.api.repository.user.UserRepository;
import com.example.JwtWithOAuth2Test.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException("Can't find username");
        }

        return UserPrincipal.create(user);
    }
}
