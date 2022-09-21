package com.example.api.services;


import com.example.api.entities.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    @Cacheable
    Page<Product> getAllProducts(Pageable page);
    Page<Product> findByTitle(String title);
    @Transactional
    void saveOrUpdateProduct(@Valid Product product, MultipartFile file) throws IOException;
    void deleteProductById(Long id);
    Product getProductById(Long id);
    @Cacheable
    Page<Product> findByPagination(int pageNo);

}
