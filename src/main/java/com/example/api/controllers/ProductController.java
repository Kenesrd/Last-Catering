package com.example.api.controllers;

import com.example.api.dto.ProductDto;
import com.example.api.dto.UserDto;
import com.example.api.entities.Cart;
import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.entities.Role;
import com.example.api.services.*;
import com.example.api.services.props.SortBy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@SessionAttributes("cart")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService typeService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model,@RequestParam(value = "title", required = false) String title){
        if (title != null && title != ""){
            model.addAttribute("products", productService.findByTitle(title));
            model.addAttribute("title", title);
            return "index";
        }
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
    @GetMapping("/{id}/cart")
    public String addProductToCart(@PathVariable(value = "id") Long productId, Principal principal){
//        model.addAttribute("products", productService.getAllProducts());
        productService.addProductToUserCart(productId, principal.getName());
        return "redirect:/";
    }
    @GetMapping("/header")
    public String header(Model model){
        model.addAttribute("text", "Hello");
        return "fragments/admin-panel-header";
    }

//    @GetMapping("/css")
//    public String styles(){
//        return "style";
//    }
//    @GetMapping("/img")
//    public String styles(){
//        return "style";
//    }
//    @GetMapping("/js")
//    public String javaScript(){
//        return "script";
//    }

//  Для АДМИНА


//    @ModelAttribute("type")
//    public ProductType type(){
//        return new ProductType();
//    }

    @GetMapping("/search")
    public String products(@RequestParam(name = "title", required = false) String title, Model model, Principal principal){
        if (title != null && title != ""){
            model.addAttribute("title", title);
            List<ProductDto> productDtos = productService.findByTitle(title);
            model.addAttribute("products", productDtos);
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            model.addAttribute("totalElements", productDtos.size());

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
        return "redirect:/admin";
    }







    @GetMapping("/page/{pageNo}/delete/{id}")
    public String deleteProduct(@PathVariable int pageNo, @PathVariable Long id){
        productService.deleteProductById(id);
        Page<ProductDto> page = productService.findByPagination(pageNo);
        if (page.getSize() <= 0 && page.isEmpty()){
            return "redirect:/admin/page/" + --pageNo;
        }
        return "redirect:/admin";
    }

    @GetMapping("/page/{pageNo}/filter/{sortBy}") // upid, Downid,
    public String filterBy(@PathVariable int pageNo,
                           @PathVariable SortBy sortBy){
        System.out.println(sortBy);

        if (pageNo == 1){
            productService.filterBy(sortBy);
            return "redirect:/admin";
        }
        productService.filterBy(sortBy);
        return "redirect:/admin/page/" + pageNo;
    }


}
