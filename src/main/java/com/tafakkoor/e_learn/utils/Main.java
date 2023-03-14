package com.tafakkoor.e_learn.utils;

import com.tafakkoor.e_learn.repository.AuthUserRepository;
import com.tafakkoor.e_learn.services.ScheduleService;

public class Main {
    private final AuthUserRepository authUserRepository;

    public Main(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public static void main(String[] args) {

        ScheduleService scheduleService = new ScheduleService();

        scheduleService.sendBirthdayEmails();
        scheduleService.deleteExpiredTokens();
        scheduleService.deleteInactiveUsers();
        scheduleService.sendEmailToInactiveUsers();

    }

}
