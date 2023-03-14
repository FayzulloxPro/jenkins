package com.tafakkoor.e_learn.utils;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.domain.Token;

import java.time.LocalDateTime;

public class Util {
    private static final ThreadLocal<Util> UTIL_THREAD_LOCAL = ThreadLocal.withInitial(Util::new);

    public Token buildToken(String token, AuthUser authUser) {
        return Token.builder()
                .token(token)
                .user(authUser)
                .validTill(LocalDateTime.now().plusMinutes(10))
                .build();
    }

public String generateBody(String username, String token){
    String link = Container.BASE_URL + "auth/activate?token=" + token;
    return """
                Subject: Activate Your Account
                                
                Dear %s,
                                
                Thank you for registering on our website. To activate your account, please click on the following link:
                                
                %s
                                
                If you have any questions or need assistance, please contact us at [SUPPORT_EMAIL OR TELEGRAM_BOT].
                                
                Best regards,
                E-Learn LTD.
                """.formatted(username, link);
}



    public String generateBodyForInactiveUsers(String username){
        return """
                Subject: Login to Your Account
                                
                Dear %s,
                                
                // message
                                
                %s
                                
                If you have any questions or need assistance, please contact us at [SUPPORT_EMAIL OR TELEGRAM_BOT].
                                
                Best regards,
                E-Learn LTD.
                """.formatted(username,Container.BASE_URL); // TODO: 13/03/23 write message to users that 3 days inactive
    }


    public String generateBodyForBirthDay(String username){
        return """
                Subject: Happy Birthday
                                
                Dear %s,
                                
                // message
                                
                %s
                                
                If you have any questions or need assistance, please contact us at [SUPPORT_EMAIL OR TELEGRAM_BOT].
                                
                Best regards,
                E-Learn LTD.
                """.formatted(username,Container.BASE_URL); // TODO: 13/03/23 write message to celebrate birtday
    }


    public static Util getInstance() {
        return UTIL_THREAD_LOCAL.get();
    }
}
