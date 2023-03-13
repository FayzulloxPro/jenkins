package com.tafakkoor.e_learn.utils;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.domain.Token;
import com.tafakkoor.e_learn.dto.UserRegisterDTO;

import java.time.LocalDateTime;

public class Util {
    private static final ThreadLocal<Util> UTIL_THREAD_LOCAL = ThreadLocal.withInitial(Util::new);

    public Token buildToken(String token, AuthUser authUser) {
        return Token.builder()
                .token(token)
                .user(authUser)
                .validTill(LocalDateTime.now().plusMinutes(5))
                .build();
    }




    public static Util getInstance() {
        return UTIL_THREAD_LOCAL.get();
    }
}
