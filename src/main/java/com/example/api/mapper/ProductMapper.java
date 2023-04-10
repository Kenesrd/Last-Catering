package com.example.api.mapper;

import com.example.api.dto.ProductDto;
import com.example.api.entities.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

//    @Mapping(source = "productDto.id", target = "id")
//    @Mapping(source = "productDto.title", target = "title")
//    @Mapping(source = "productDto.description", target = "description")
//    @Mapping(source = "productDto.weightAndPiece", target = "weightAndPiece")
//    @Mapping(source = "productDto.price", target = "price")
//    @Mapping(source = "productDto.createdAt", target = "createdAt")
//    @Mapping(source = "productDto.imageDto", target = "image")
//    @Mapping(source = "productDto.productType", target = "productType")
    Product toProduct(ProductDto productDto);

    @InheritInverseConfiguration
    ProductDto fromProduct(Product product);

    List<Product> toProductList (List<ProductDto> productDtos);
//    @InheritInverseConfiguration
    List<ProductDto> fromProductList(List<Product> products);
}
