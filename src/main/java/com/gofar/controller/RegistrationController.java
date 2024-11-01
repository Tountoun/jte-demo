package com.gofar.controller;


import com.gofar.entity.User;
import com.gofar.exception.UserException;
import com.gofar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Controller
public class RegistrationController {

    private final UserService userService;
    public static final String REGISTER_TEMPLATE = "register";

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return REGISTER_TEMPLATE;
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        HashMap<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            return REGISTER_TEMPLATE;
        }

        try {
            this.userService.registerUser(user);
        } catch (UserException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return REGISTER_TEMPLATE;
        }
        return "redirect:/register?success";
    }
}
