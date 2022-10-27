package com.example.api.controllers;

import com.example.api.dto.UserDto;
import com.example.api.entities.Product;
import com.example.api.entities.Role;
import com.example.api.services.ProductServiceImpl;
import com.example.api.services.ProductTypeService;
import com.example.api.services.UserService;
import com.example.api.services.props.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductTypeService typeService;
    @Autowired
    private UserService userService;



    @GetMapping
    public String showAllProducts(Model model, Principal principal){
        Page<Product> page= productService.findByPagination(1);
        List<Product> listProducts = page.getContent();
        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("text", "Hello");
        model.addAttribute("product", new Product());
        model.addAttribute("image", null);
        model.addAttribute("types", typeService.productTypeList());
        model.addAttribute("sort", SortBy.class);
        model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
        model.addAttribute("users", userService.getAllUsers().size());
        model.addAttribute("admins", userService.findUsersByRole(Role.ADMIN).size());
        UserDto userDto = userService.findByEmail(principal.getName());
        model.addAttribute("user", userDto.getEmail());
        return "admin-panel";
    }



//    ====================PAGINATION========================================
    @GetMapping("/page/{pageNo}")
    public String display(@PathVariable (value = "pageNo") int pageNo, Model model, Principal principal) {

        if (pageNo == 1){
            return "redirect:/admin";
        }

        Page<Product> page= productService.findByPagination(pageNo);
        List<Product> listProducts = page.getContent();
        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());


        model.addAttribute("product", new Product());
        model.addAttribute("image", null);
        model.addAttribute("types", typeService.productTypeList());

        model.addAttribute("sort", SortBy.class);
        model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
        model.addAttribute("users", userService.getAllUsers().size());
        model.addAttribute("admins", userService.findUsersByRole(Role.ADMIN).size());
        UserDto userDto = userService.findByEmail(principal.getName());
        model.addAttribute("user", userDto.getEmail());
        return "admin-panel";

    }

    @GetMapping("/header")
    public String header(){
        return "fragments/admin-panel-header";
    }



}
