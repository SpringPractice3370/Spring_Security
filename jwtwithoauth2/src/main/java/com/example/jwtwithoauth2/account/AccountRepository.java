package com.example.jwtwithoauth2.account;

import com.example.jwtwithoauth2.auth.oauth2.model.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByEmailAndProvider(String email, OAuth2Provider provider);
}
