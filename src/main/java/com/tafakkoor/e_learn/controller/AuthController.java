package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.dto.UserRegisterDTO;
import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
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
    public String register(@Valid @ModelAttribute UserRegisterDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        if (!dto.password().equals(dto.confirmPassword())) {
            result.rejectValue("confirmPassword", "", "Passwords do not match");
            return "auth/register";
        }
        AuthUser authUser = AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .email(dto.email())
                .build();
        authUserRepository.save(authUser);
        return "redirect:/verify";
    }

}
