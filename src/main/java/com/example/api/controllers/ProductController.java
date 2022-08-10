package com.example.api.controllers;

import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.services.ImageService;
import com.example.api.services.ProductService;
import com.example.api.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService typeService;
    @Autowired
    private ImageService imageService;

    @GetMapping
    public String index(Model model,@RequestParam(value = "title", required = false) String title){

        if (title != null && title != ""){
            model.addAttribute("products", productService.getListProducts(title));
            model.addAttribute("title", title);
            return "index";
        }
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }


//  Для АДМИНА


//    @ModelAttribute("type")
//    public ProductType type(){
//        return new ProductType();
//    }

//    @GetMapping("/search")
//    public String products(@RequestParam(name = "title", required = false) String title, Model model){
//        model.addAttribute("products", productService.getListProducts(title));
//        return "products";
//    }

//    @GetMapping("/product/{id}")
//    public String productInfo(@PathVariable Long id, Model model){
//        model.addAttribute("product", productService.getProductById(id));
//        return "product-info";
//    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        Product productById = productService.getProductById(id);
        model.addAttribute("product", productById);
        if (productById.getImage() != null){
            Image imgId = imageService.findById(productById.getImageId());
            model.addAttribute("image", imgId.getId());
        } else {
            model.addAttribute("image", -1L);
        }

        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("types", typeService.productTypeList());
        return "admin-panel";
    }

    @PostMapping("/create")
    public String createOrUpdateProduct(@ModelAttribute Product product, Long image,
                                @RequestParam MultipartFile file) throws IOException {
        System.out.println("==========IMAGE UID :" + image);
        productService.saveOrUpdateProduct(product, file);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return "redirect:/admin";
    }
}
