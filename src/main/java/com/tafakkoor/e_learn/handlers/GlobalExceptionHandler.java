package com.tafakkoor.e_learn.handlers;

import com.tafakkoor.e_learn.exceptions.CustomRuntimeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.tafakkoor.e_learn")
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomRuntimeException.class,})
    public String exception(Model model, RuntimeException e) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
