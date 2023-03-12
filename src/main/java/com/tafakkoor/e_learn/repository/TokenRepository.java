package com.tafakkoor.e_learn.repository;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, AuthUser> {
        Optional<Token> findByUser(AuthUser user);
        Optional<Token> findByToken(String token);

}
