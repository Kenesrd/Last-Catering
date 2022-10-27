package com.example.api.controllers;

import com.example.api.dto.UserDto;
import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.entities.Role;
import com.example.api.services.ImageService;
import com.example.api.services.ProductServiceImpl;
import com.example.api.services.ProductTypeService;
import com.example.api.services.UserService;
import com.example.api.services.props.SortBy;
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
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductTypeService typeService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model,@RequestParam(value = "title", required = false) String title){
        if (title != null && title != ""){
            model.addAttribute("products", productService.findByTitle(title));
            model.addAttribute("title", title);
            return "index";
        }
        Pageable page = PageRequest.of(0,30, Sort.by("id").descending());
        model.addAttribute("products", productService.getAllProducts(page));
        return "index";
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
    public String products(@RequestParam(name = "title", required = false) String title, Model model){
        if (title != null && title != ""){
            model.addAttribute("title", title);
            Page<Product> page= productService.findByTitle(title);
            model.addAttribute("products", page.getContent());
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("totalElements", page.getTotalElements());

            model.addAttribute("product", new Product());
            model.addAttribute("image", null);
            model.addAttribute("types", typeService.productTypeList());
            model.addAttribute("sort", SortBy.class);
            model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
            return "admin-panel";
        }
        return "redirect:/admin";
    }



    @GetMapping("/page/{pageNo}/edit/{id}")
    public String editProduct(@PathVariable int pageNo, @PathVariable Long id, Model model, Principal principal){
        Page<Product> page = productService.findByPagination(pageNo);
        List<Product> products = page.getContent();
        Product productById = productService.getProductById(id);
        model.addAttribute("products", products);
        model.addAttribute("product", productById);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("sort", SortBy.class);
        model.addAttribute("trigger", productService.getTrigger()); // Рычаг для сортировки
        model.addAttribute("users", userService.getAllUsers().size());
        model.addAttribute("admins", userService.findUsersByRole(Role.ADMIN).size());
        UserDto userDto = userService.findByEmail(principal.getName());
        model.addAttribute("user", userDto.getEmail());

        if (productById.getImage() != null){
            Image imgId = imageService.findById(productById.getImageId());
            model.addAttribute("image", imgId.getId());
        } else {
            model.addAttribute("image", null);
        }
        model.addAttribute("types", typeService.productTypeList());
        return "admin-panel";
    }

    @PostMapping("/create")
    public String createOrUpdateProduct(@ModelAttribute Product product,
                                        Errors errors,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam int pageNo,
                                        @RequestParam String typeWeightDetail) throws IOException {
//        if (product.getId() == null){ // Если добавим Новый Продукт то перенаправляем на 1 страницу
//            productService.saveOrUpdateProduct(product, file);
//            return "redirect:/admin";
//        }
        if (errors.hasErrors()){
            return "admin-panel";
        }
        productService.saveOrUpdateProduct(product, file, typeWeightDetail);
        return "redirect:/admin/page/" + pageNo;
    }

    @GetMapping("/page/{pageNo}/delete/{id}")
    public String deleteProduct(@PathVariable int pageNo, @PathVariable Long id){
        productService.deleteProductById(id);
        Page<Product> page = productService.findByPagination(pageNo);
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
