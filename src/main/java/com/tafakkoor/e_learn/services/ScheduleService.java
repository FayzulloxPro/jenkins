package com.tafakkoor.e_learn.services;

import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import com.tafakkoor.e_learn.repository.TokenRepository;
import com.tafakkoor.e_learn.utils.Util;
import com.tafakkoor.e_learn.utils.mail.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ScheduleService {

    AuthUserRepository authUserRepository;

    TokenRepository tokenRepository;

    // send email to user that have not logged in for 3 days
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24) // 1 day
    public void sendEmailToInactiveUsers() { // done
        List<AuthUser> users = authUserRepository.findByLastLoginBefore(LocalDateTime.now().plusDays(3));
        EmailService emailService = EmailService.getInstance();
        for ( AuthUser user : users ) {
            CompletableFuture.runAsync(() -> emailService.sentEmailToInactiveUsers(user.getEmail(),
                    Util.getInstance().generateBodyForInactiveUsers(user.getUsername())
                    ,"Login to Your Account"));
        }

    }
    
    @Scheduled(fixedRate = 1000 * 60 * 5) //  5 minutes
    public void deleteExpiredTokens() { // done
        tokenRepository.deleteByValidTillBeforeAllIgnoreCase(LocalDateTime.now());
    }
    
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24) // 1 day
    public void deleteInactiveUsers() {   // done
        authUserRepository.deleteByStatusInActive();
    }

    // schedule for a task to be executed every day at 12:00 am
    @Scheduled(cron = "0 0 0 * * *")
    public void sendBirthdayEmails() { // TODO: 3/12/23  // send email to users that have birthday today
        System.out.println("scheduled task is running");
    }
}
