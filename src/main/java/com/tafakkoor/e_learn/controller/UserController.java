package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.config.security.UserSession;
import com.tafakkoor.e_learn.enums.Levels;
import com.tafakkoor.e_learn.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller

public class UserController {
    private final UserSession userSession;
    private final UserService userService;

    public UserController(UserSession userSession, UserService userService) {
        this.userSession = userSession;
        this.userService = userService;
    }

    @GetMapping("/practise")
    public String practise() {
        return "user/practise";
    }
    @GetMapping("/practise/story")
    public ModelAndView story() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userLevel", userSession.getLevel());
        modelAndView.setViewName("user/levelsStory");
        List<Levels> levelsList=userService.getLevels(userSession.getLevel());
        modelAndView.addObject("levels", levelsList);
        return modelAndView;
    }
    @GetMapping("/practise/grammar")
    public ModelAndView grammar() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userLevel", userSession.getLevel());
        modelAndView.setViewName("user/levelsGrammar");
        List<Levels> levelsList=userService.getLevels(userSession.getLevel());
        modelAndView.addObject("levels", levelsList);
        return modelAndView;
    }

}
