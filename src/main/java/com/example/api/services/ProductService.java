package com.example.api.services;


import com.example.api.dto.ProductDto;
import com.example.api.services.props.SortBy;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


public interface ProductService {
    Page<ProductDto> getAllProducts();
    List<ProductDto> findByTitle(String title);
    void saveOrUpdateProduct(ProductDto productDto, MultipartFile file, String typeWeightDetail) throws IOException;
    void deleteProductById(Long id);
    ProductDto getProductById(Long id);
    Page<ProductDto> findByPagination(int pageNo);
    void addProductToUserCart(Long productId, String username);
    boolean getTrigger();

    void filterBy(SortBy sortBy);
}
