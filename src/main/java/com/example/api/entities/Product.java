package com.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
/*

 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "description", columnDefinition = "text")
    private String description;
//    private long rating;
//    @Enumerated(EnumType.STRING)
    private String weightAndPiece;
    private BigDecimal price;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "img_prod",
                joinColumns = @JoinColumn(name = "prod_id"),
                inverseJoinColumns = @JoinColumn(name="img_id"))
    private Image image;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private ProductType productType;

    public Long getImageId(){
        return this.image.getId();
    }

}
