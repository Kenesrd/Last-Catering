package com.example.api.controllers;

import com.example.api.dto.CartDto;
import com.example.api.services.CartService;
import com.example.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping
    public CartDto aboutCart(Principal principal) {
//        if (principal == null){
//            model.addAttribute("cart", new CartDto());
//        } else {
        CartDto cartByUser = cartService.findCartByUser(userService.findByEmail(principal.getName()));
        return cartByUser;
//        }
    }
}
