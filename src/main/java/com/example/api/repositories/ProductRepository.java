package com.example.api.repositories;

import com.example.api.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAll();
//    List<Product> findAllByIdOrderByIdAsc(Sort sort);

//    List<Product> getAllProducts();
    List<Product> findByTitle(String title);
    void deleteById(Long id);
    Optional<Product> findById(Long id);
    List<Product> findAllByTitleIgnoreCaseStartsWith(String title);
}
