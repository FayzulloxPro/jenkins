package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.UserRegisterDTO;
import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
        System.out.println("Abdullo");
        System.out.println("hello from fayzullo branch");
        System.out.println("hello from dilshod branch");
        System.out.println("Test 33");
        System.out.println("Nimadir");
        System.out.println("Nimadir232");
        System.out.println("Nimadir5678");
        System.out.println("yana nimadir");
        System.out.println("Yana nimadir 22222");
        System.out.println("Yana nimadir from DIlshod");
        System.out.println("Yana nimadir from Abdullo 2222");
        System.out.println("Yana nimadir from Abdullo 3333");
        System.out.println("Yana nimadir from Abdullo 4444");
        System.out.println("Yana nimadir from Abdullo 5555");
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        var mav = new ModelAndView();
        mav.setViewName("auth/register");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(required = false) String error) {
        var mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("auth/login");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logoutPage() {
        var mav = new ModelAndView();
        mav.setViewName("auth/logout");
        return mav;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterDTO dto) {
        AuthUser authUser = AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .email(dto.email())
                .build();
        authUserRepository.save(authUser);
        return "redirect:/login";
    }

}