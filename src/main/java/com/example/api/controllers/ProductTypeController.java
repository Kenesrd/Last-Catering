package com.example.api.controllers;

import com.example.api.entities.ProductType;
import com.example.api.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/product-types")
@RequestMapping("/types")
public class ProductTypeController {

    @Autowired
    private ProductTypeService typeService;


    @GetMapping
    public String allTypes(Model model){
        ProductType productType = new ProductType();
        model.addAttribute("productType", productType);
        model.addAttribute("prodtypes", typeService.productTypeList());
        return "type-of-products";
    }
    @GetMapping("/edit/{id}")
    public String update(Model model, @PathVariable Long id){
        model.addAttribute("productType", typeService.findById(id));
        model.addAttribute("prodtypes", typeService.productTypeList());
        return "type-of-products";
    }

    @PostMapping("/update")
    public String createType(@ModelAttribute("productType") ProductType productType){
        typeService.saveOrUpdate(productType);
        return "redirect:/types";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        typeService.deleteProductTypeById(id);
        return "redirect:/types";
    }
}
