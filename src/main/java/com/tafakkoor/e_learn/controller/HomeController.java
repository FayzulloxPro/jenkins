package com.tafakkoor.e_learn.controller;

import com.tafakkoor.e_learn.config.security.UserSession;
import com.tafakkoor.e_learn.exceptions.PermissionDeniedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
public class HomeController {

    private final UserSession userSession;

    public HomeController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/home")
    public String hasAdminRole(Model model, Principal principal, HttpServletRequest request, ServletResponse res) throws ServletException, IOException {
//        request.getRequestDispatcher("404.html").forward(request, res);
//        throw new PermissionDeniedException("You don't have permission to access this page");
        return "home";
    }

    @GetMapping("/home2")
    /*@RolesAllowed("ADMIN")*/
    public String homePage2() {
//        if (new Random().nextBoolean()) {
//            throw new CustomRuntimeException("Just For Fun Exception");
//        }
        return "main2";
    }

/*    @ExceptionHandler(CustomRuntimeException.class)
    public String exception(Model model, CustomRuntimeException e) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }*/

//    @GetMapping("/upload")
//    public String uploadPage() {
//        return "uploadfile";
//    }
//
//    @PostMapping("/upload")
//    public String upload(@ModelAttribute UserDTO dto, @RequestParam("file[]") MultipartFile[] files) throws IOException {
//        System.out.println(dto);
//        for (MultipartFile file : files) {
//            Files.copy(file.getInputStream(), Path.of("/home/jlkesh/Desktop", file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
//        }
//        return "redirect:/upload";
//    }
}