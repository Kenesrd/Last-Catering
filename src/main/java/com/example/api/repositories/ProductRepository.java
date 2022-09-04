package com.example.api.repositories;

import com.example.api.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
//    @Query(value = "SELECT * FROM products ORDER BY id limit 10", nativeQuery = true)
//    Iterable<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    List<Product> findByTitle(String title);
    void deleteById(Long id);
    Optional<Product> findById(Long id);
    List<Product> findAllByTitleIgnoreCaseStartsWith(String title);
//    Page<Product> findByPagination(Pageable pageable);
}
