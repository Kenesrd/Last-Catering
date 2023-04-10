package com.example.api.controllers;

import com.example.api.entities.Cart;
import com.example.api.services.RegistrationForm;
import com.example.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @ModelAttribute(name = "cart")
    public Cart cart(){
        return new Cart();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String register(Model model){
        model.addAttribute("register", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){

        userService.save(form.toUserDto(passwordEncoder));
        return "redirect:/login";
    }
}
