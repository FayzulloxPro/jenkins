package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.config.security.UserSession;
import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.enums.Levels;
import com.tafakkoor.e_learn.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        AuthUser user = userService.getUser(userSession.getId());
        List<Levels> levelsList = userService.getLevels(user.getLevel());
        modelAndView.addObject("levels", levelsList);
        return modelAndView;
    }

    @GetMapping("/practise/grammar")
    public ModelAndView grammar() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("levelNotFound" , null);

        modelAndView.addObject("userLevel", userSession.getLevel());
        modelAndView.setViewName("user/levelsGrammar");
        AuthUser user = userService.getUser(userSession.getId());
        List<Levels> levelsList = userService.getLevels(user.getLevel());
        modelAndView.addObject("levels", levelsList);
        return modelAndView;
    }

    @GetMapping("/test")
    public String test() {
        return "user/test";
    }
    @PostMapping("/test")
    public String testPost(HttpServletRequest request) {
        String title = request.getParameter("title");
        String content = request.getParameter("editor_content");
        System.out.println("title = " + title);
        System.out.println("content = " + content);
        return "redirect:/test";
    }

    @GetMapping("/practise/grammar/{level}")
    public ModelAndView testGrammar(@PathVariable String level) {
        System.out.println("level = " + level);
        ModelAndView modelAndView = new ModelAndView();
        try {
            Levels levels = Levels.valueOf(level.toUpperCase());
        }catch (Exception e){
            modelAndView.addObject("levelNotFound" , "Level not found named "+level);

            modelAndView.setViewName("redirect:/practise/grammar");
            return modelAndView;
        }
        modelAndView.setViewName("user/testGrammar");
        modelAndView.addObject("level", level);
        return modelAndView;
    }

    public static void main(String[] args) {
        Levels levels = Levels.valueOf("A1".toUpperCase());
        System.out.println("levels = " + levels);
    }

}
