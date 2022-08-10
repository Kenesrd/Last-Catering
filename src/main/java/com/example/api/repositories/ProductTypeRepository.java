package com.example.api.repositories;

import com.example.api.entities.Product;
import com.example.api.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    ProductType findByTypeName(String name);
    Optional<ProductType> findById(Long id);
}
