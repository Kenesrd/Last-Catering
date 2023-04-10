package com.example.api.controllers;

import com.example.api.dto.ProductDto;
import com.example.api.dto.UserDto;
import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.entities.Role;
import com.example.api.services.*;
import com.example.api.services.props.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;
    @Autowired
    private ProductTypeService typeService;
    @Autowired
    private UserService userService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAllProducts(Model model, Principal principal){
        Page<ProductDto> page = productService.findByPagination(1);
        List<ProductDto> listProducts = page.getContent();
        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("text", "Hello");
        model.addAttribute("product", new ProductDto());
        model.addAttribute("types", typeService.productTypeList());
        model.addAttribute("sort", SortBy.class);
        model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
        model.addAttribute("users", userService.getAllUsers().size());
        model.addAttribute("admins", userService.findUsersByRole(Role.ADMIN).size());
        UserDto userDto = userService.findByEmail(principal.getName());
        model.addAttribute("user", userDto.getEmail());
        return "admin-panel";
    }

    //===============CREATE+UPDATE===========
    @PostMapping
    public String createOrUpdateProduct(ProductDto product,
                                        Errors errors,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam int pageNo,
                                        @RequestParam String typeWeightDetail, Model model, Principal principal) throws IOException {
//        if (product.getId() == null){ // Если добавим Новый Продукт то перенаправляем на 1 страницу
//            productService.saveOrUpdateProduct(product, file);
//            return "redirect:/admin";
//        }
        if (errors.hasErrors()){
            Page<ProductDto> page= productService.findByPagination(1);
            List<ProductDto> listProducts = page.getContent();
            model.addAttribute("products", listProducts);
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalElements", page.getTotalElements());
            model.addAttribute("text", "Hello");
            model.addAttribute("types", typeService.productTypeList());
            model.addAttribute("sort", SortBy.class);
            model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
            model.addAttribute("users", userService.getAllUsers().size());
            model.addAttribute("admins", userService.findUsersByRole(Role.ADMIN).size());
            UserDto userDto = userService.findByEmail(principal.getName());
            model.addAttribute("user", userDto.getEmail());
            return "admin-panel";
        }
        productService.saveOrUpdateProduct(product, file, typeWeightDetail);

        return "redirect:/admin/page/" + pageNo;
    }

//    ====================PAGINATION========================================
    @GetMapping("/page/{pageNo}")
    public String display(@PathVariable (value = "pageNo") int pageNo, Model model, Principal principal) {

        if (pageNo == 1){
            return "redirect:/admin";
        }

        Page<ProductDto> page= productService.findByPagination(pageNo);
        List<ProductDto> listProducts = page.getContent();
        model.addAttribute("products", listProducts);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());


        model.addAttribute("product", new ProductDto());
        model.addAttribute("types", typeService.productTypeList());

        model.addAttribute("sort", SortBy.class);
        model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
        model.addAttribute("users", userService.getAllUsers().size());
        model.addAttribute("admins", userService.findUsersByRole(Role.ADMIN).size());
        UserDto userDto = userService.findByEmail(principal.getName());
        model.addAttribute("user", userDto.getEmail());
        return "admin-panel";

    }
    @GetMapping("/page/{pageNo}/edit/{id}")
    public String editProduct(@PathVariable int pageNo, @PathVariable Long id, Model model, Principal principal){
        Page<ProductDto> page = productService.findByPagination(pageNo);
        List<ProductDto> products = page.getContent();
        model.addAttribute("products", products);
        ProductDto productById = productService.getProductById(id);
        model.addAttribute("product", productById);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("text", "Hello");
        model.addAttribute("sort", SortBy.class);
        model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
        model.addAttribute("users", userService.getAllUsers().size());
        model.addAttribute("admins", userService.findUsersByRole(Role.ADMIN).size());
        UserDto userDto = userService.findByEmail(principal.getName());
        model.addAttribute("user", userDto.getEmail());
        model.addAttribute("types", typeService.productTypeList());

        return "admin-panel";
    }







    @GetMapping("/header")
    public String header(){
        return "fragments/admin-panel-header";
    }



}
