package com.example;

import com.example.api.entities.Product;
import com.example.api.entities.ProductType;
import com.example.api.entities.details.WeightAndPiece;
import com.example.api.repositories.ProductRepository;
import com.example.api.repositories.ProductTypeRepository;
import com.example.api.services.props.PageSizeProps;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

@SpringBootApplication
//@EnableConfigurationProperties(PageSizeProps.class)
//@ComponentScan(basePackages = {"com.example.api"})
public class CateringAktauApplication {

    public static void main(String[] args) {
        SpringApplication.run(CateringAktauApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner (ProductRepository productRepository) {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                Product product = new Product();
//                product.setId(1L);
//                product.setTitle("product1");
//                product.setDescription("fkjsdnfjsnafljks");
//                product.setPrice(new BigDecimal(12));
//
//                productRepository.save(product);
//
//                product.setId(3L);
//                product.setTitle("product2");
//                product.setDescription("fkjsdnfjsnafljks");
//                product.setPrice(new BigDecimal(123));
//
//                productRepository.save(product);
//
//                product.setId(3L);
//                product.setTitle("product3");
//                product.setDescription("fkjsdnfjsnafljks");
//                product.setPrice(new BigDecimal(11));
//
//                productRepository.save(product);
//
//                product.setId(4L);
//                product.setTitle("Americ");
//                product.setDescription("fkjsdnfjsnafljks");
//                product.setPrice(new BigDecimal(1100));
//
//                productRepository.save(product);
//            }
//        };
//    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner2 (ProductTypeRepository typeRepository) {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                ProductType type1 = new ProductType();
//                type1.setId(1L);
//                type1.setTypeName("sss");
//                typeRepository.save(type1);
//
//                ProductType type2 = new ProductType();
//                type2.setId(2L);
//                type2.setTypeName("ddd");
//                typeRepository.save(type2);
//
//                ProductType type3 = new ProductType();
//                type3.setId(3L);
//                type3.setTypeName("aaa");
//                typeRepository.save(type3);
//            }
//        };
//    }

}
