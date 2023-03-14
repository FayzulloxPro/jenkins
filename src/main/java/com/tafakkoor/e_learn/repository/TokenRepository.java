package com.tafakkoor.e_learn.repository;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, AuthUser> {


        @Query( "delete from Token t where t.validTill < :validTill" )
        void deleteByValidTillBefore( LocalDateTime validTill );

        Optional<Token> findByUser(AuthUser user);
        Optional<Token> findByToken(String token);

}
