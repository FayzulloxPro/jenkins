package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.exceptions.CustomRuntimeException;
import com.tafakkoor.e_learn.config.security.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Controller
public class HomeController {

    private final UserSession userSession;

    public HomeController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/home")
    public String hasAdminRole(Model model) {
        System.out.println("userSession.getUser().getId() = " + userSession.getId());
        return "home";
    }

    @GetMapping("/home2")
    /*@RolesAllowed("ADMIN")*/
    public String homePage2() {
        if (new Random().nextBoolean()) {
            throw new CustomRuntimeException("Just For Fun Exception");
        }
        return "main2";
    }

/*    @ExceptionHandler(CustomRuntimeException.class)
    public String exception(Model model, CustomRuntimeException e) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }*/

    @GetMapping("/upload")
    public String uploadPage() {
        return "uploadfile";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute UserDTO dto, @RequestParam("file[]") MultipartFile[] files) throws IOException {
        System.out.println(dto);
        for (MultipartFile file : files) {
            Files.copy(file.getInputStream(), Path.of("/home/jlkesh/Desktop", file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }
        return "redirect:/upload";
    }
}

record UserDTO(String fname, String lname) {
}