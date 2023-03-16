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
    @Query( "DELETE FROM AuthUser a WHERE a.status = 1 AND a.createdAt < NOW() - INTERVAL('10 minutes')" )
    int deleteByStatusInActive();
    List<AuthUser> findByLastLoginBefore( LocalDateTime lastLogin );
    List<AuthUser> findByDeleted( boolean deleted);


    Optional<AuthUser> findByUsernameIgnoreCase( String username );

    AuthUser findById( Long id );


    @Query( "select a from AuthUser a  where a.birthDate = :now" )
    List<AuthUser> findAllByBirtDate(LocalDateTime now);

}
