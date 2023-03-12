package com.tafakkoor.e_learn.repository;

import com.tafakkoor.e_learn.domain.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
    Optional<AuthUser> findByUsernameIgnoreCase(String username);
    boolean activateUser(String username);

}
