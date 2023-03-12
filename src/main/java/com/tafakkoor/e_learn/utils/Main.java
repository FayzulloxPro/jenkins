package com.tafakkoor.e_learn.utils;

import com.tafakkoor.e_learn.controller.AuthController;
import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.enums.Status;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import com.tafakkoor.e_learn.utils.mail.EmailService;

public class Main {
    private final AuthUserRepository authUserRepository;

    public Main(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public static void main(String[] args) {
        EmailService.send("dilgan0819@gmail.com", "Test", "Zo'r zo'r");
    }
}
