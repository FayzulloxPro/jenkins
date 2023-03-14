package com.tafakkoor.e_learn.utils;

import com.tafakkoor.e_learn.repository.AuthUserRepository;
import com.tafakkoor.e_learn.repository.TokenRepository;

public class Main {
    private final AuthUserRepository authUserRepository;
    private final TokenRepository tokenRepository;

    public Main( AuthUserRepository authUserRepository, TokenRepository tokenRepository ) {
        this.authUserRepository = authUserRepository;
        this.tokenRepository = tokenRepository;
    }

    public static void main(String[] args) {

    }

}
