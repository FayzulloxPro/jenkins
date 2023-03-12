package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.domain.Token;
import com.tafakkoor.e_learn.dto.UserRegisterDTO;
import com.tafakkoor.e_learn.domain.AuthUser;
import com.tafakkoor.e_learn.enums.Status;
import com.tafakkoor.e_learn.repository.AuthUserRepository;
import com.tafakkoor.e_learn.repository.TokenRepository;
import com.tafakkoor.e_learn.utils.Container;
import com.tafakkoor.e_learn.utils.mail.EmailService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping( "/auth" )
public class AuthController {
    private final AuthUserRepository authUserRepository;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController( AuthUserRepository authUserRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder ) {
        this.authUserRepository = authUserRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping( "/register" )
    public ModelAndView registerPage() {
        var mav = new ModelAndView();
        mav.setViewName("auth/register");
        return mav;
    }

    @GetMapping( "/login" )
    public ModelAndView loginPage( @RequestParam( required = false ) String error ) {
        var mav = new ModelAndView();
        mav.addObject("error", error);
        mav.setViewName("auth/login");
        return mav;
    }


    @GetMapping( "/logout" )
    public ModelAndView logoutPage() {
        var mav = new ModelAndView();
        mav.setViewName("auth/logout");
        return mav;
    }

    @PostMapping( "/register" )
    public String register( @Valid @ModelAttribute UserRegisterDTO dto, BindingResult result ) {
        if ( result.hasErrors() ) {
            return "auth/register";
        }
        if ( !dto.password().equals(dto.confirmPassword()) ) {
            result.rejectValue("confirmPassword", "", "Passwords do not match");
            return "auth/register";
        }
        AuthUser authUser = AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .email(dto.email())
                .build();
        authUserRepository.save(authUser);

        String token = UUID.randomUUID().toString();  // TODO: 3/12/23 encrypt token
        String email = authUser.getEmail();

        String link = Container.BASE_URL + "auth/activate?token=" + token;
        System.out.println(link);

        String body = """
                Subject: Activate Your Account
                                
                Dear %s,
                                
                Thank you for registering on our website. To activate your account, please click on the following link:
                                
                %s
                                
                If you have any questions or need assistance, please contact us at [SUPPORT_EMAIL OR TELEGRAM_BOT].
                                
                Best regards,
                E-Learn LTD.
                """.formatted(dto.username(), link);

        Token token1 = Token.builder()
                .token(token)
                .user(authUser)
                .validTill(LocalDateTime.now().plusSeconds(10))
                .build();

        tokenRepository.save(token1);

        CompletableFuture.runAsync(() -> EmailService.getInstance().sendActivationToken(email, body, "Activate Email"));
        return "auth/verify_email";
    }

    @GetMapping( "/activate" )
    public String activate( @RequestParam( name = "token" ) String token ) {
        Optional<Token> byToken = tokenRepository.findByToken(token);
        if(byToken.isPresent()){
            Token token1 = byToken.get();
            if ( !token1.getValidTill().isBefore(LocalDateTime.now()) ) {
                AuthUser user = token1.getUser();
                user.setStatus(Status.ACTIVE);
                authUserRepository.save(user);
                System.out.println(user);
                return "auth/code_activated";
            } else {
                return "auth/code_expired";
            }
        }else{
            return "auth/code_expired";
        }
    }



}
