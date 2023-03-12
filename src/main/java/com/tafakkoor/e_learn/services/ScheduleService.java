package com.tafakkoor.e_learn.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    // send email to user that have not logged in for 3 days
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24) // 1 day
    public void sendEmailToInactiveUsers() { // TODO: 3/12/23
        System.out.println("scheduled task is running");
    }
    
    @Scheduled(fixedRate = 1000 * 60 * 5) //  5 minutes
    public void deleteExpiredTokens() { // TODO: 3/12/23  
        System.out.println("scheduled task is running");
    }
    
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24) // 1 day
    public void deleteInactiveUsers() {   // TODO: 3/12/23  // delete users that have been created at least 5 minutes ago
        System.out.println("scheduled task is running");
    }

    // schedule for a task to be executed every day at 12:00 am
    @Scheduled(cron = "0 0 0 * * *")
    public void sendBirthdayEmails() { // TODO: 3/12/23  // send email to users that have birthday today
        System.out.println("scheduled task is running");
    }
}
