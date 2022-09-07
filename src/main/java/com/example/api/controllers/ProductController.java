package com.example.api.controllers;

import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.services.ImageService;
import com.example.api.services.ProductServiceImpl;
import com.example.api.services.ProductTypeService;
import com.example.api.services.props.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductTypeService typeService;
    @Autowired
    private ImageService imageService;

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

//    @GetMapping("/css")
//    public String styles(){
//        return "style";
//    }
//    @GetMapping("/img")
//    public String styles(){
//        return "style";
//    }


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


    @GetMapping("/page/{pageNo}/edit/{id}")
    public String editProduct(@PathVariable int pageNo, @PathVariable Long id, Model model){
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
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam int pageNo) throws IOException {
//        if (product.getId() == null){ // Если добавим Новый Продукт то перенаправляем на 1 страницу
//            productService.saveOrUpdateProduct(product, file);
//            return "redirect:/admin";
//        }
        productService.saveOrUpdateProduct(product, file);
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
