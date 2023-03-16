package com.tafakkoor.e_learn.repository;

import com.tafakkoor.e_learn.domain.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface AuthUserRepository extends JpaRepository<AuthUser, String> {

    @Modifying
    @Query( "update AuthUser a set a.deleted = true where a.status = 1" )
        // TODO: 13/03/23 hard delete or soft delete
    int deleteByStatusInActive();
    List<AuthUser> findByLastLoginBefore( LocalDateTime lastLogin );


    Optional<AuthUser> findByUsernameIgnoreCase( String username );

    AuthUser findById( Long id );


    @Query( "select a from AuthUser a  where a.birthDate = :now" )
    List<AuthUser> findAllByBirtDate(LocalDateTime now);

    AuthUser findByUsername(String username);
}
