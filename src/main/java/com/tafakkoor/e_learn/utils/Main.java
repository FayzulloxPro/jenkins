package com.tafakkoor.e_learn.utils;

import com.tafakkoor.e_learn.controller.AuthController;
import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.repository.AuthUserRepository;

public class Main {
    private final AuthUserRepository authUserRepository;

    public Main(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public static void main(String[] args) {
        AuthUser authUser = new AuthUser("username", "password", "email", null, AuthUser.Status.ACTIVE);
    }
}
