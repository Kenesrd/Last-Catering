package com.example.api.services;

import com.example.api.entities.ProductType;
import com.example.api.repositories.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository typeRepository;

    public List<ProductType> productTypeList(){
        return typeRepository.findAll();
    }

    public void saveOrUpdate(ProductType productType){
        typeRepository.save(productType);
    }

    public ProductType findById(Long id){
        return typeRepository.findById(id).orElse(null);
    }

    public void deleteProductTypeById(Long id){
        typeRepository.deleteById(id);
    }
}
