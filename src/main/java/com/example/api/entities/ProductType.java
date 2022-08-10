package com.example.api.entities;

import com.example.api.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "types_of_products")
public class ProductType {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String typeName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

//    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
//    private List<Product> products = new ArrayList<>();
//
//    public void addProduct(Product product){
//        this.products.add(product);
//    }
}
