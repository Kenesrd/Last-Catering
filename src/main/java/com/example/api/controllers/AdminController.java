package com.example.api.controllers;

import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.entities.ProductType;
import com.example.api.services.ProductService;
import com.example.api.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService typeService;

    @GetMapping
    public String showAllProducts(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("image", -1L);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("types", typeService.productTypeList());
        return "admin-panel";
    }

}
