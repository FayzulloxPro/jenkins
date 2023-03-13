package com.tafakkoor.e_learn.config.security;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserUserDetailsService implements UserDetailsService {
    private final AuthUserRepository authUserRepository;

    public AuthUserUserDetailsService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials"));
        return new AuthUserUserDetails(authUser);
    }

    public void save(AuthUser authUser) {
        authUserRepository.save(authUser);
    }
}
