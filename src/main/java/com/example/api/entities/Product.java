package com.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    @NotBlank
    @Size(message = "Название продукта Не должно быть пустым!", min = 2)
    private String title;
    @NotBlank
    @Size(message = "Описание продукта Не должно быть пустым!", min = 10)
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @NotBlank(message = "Выберите тип продукта")
    private String weightAndPiece;
    @Positive
    @NotNull(message = "Цена продукта Не должно быть пустым или отрицательным!")
    private BigDecimal price;
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "img_prod",
                joinColumns = @JoinColumn(name = "prod_id"),
                inverseJoinColumns = @JoinColumn(name="img_id"))
    private Image image;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private ProductType productType;

    public Long getImageId(){
        return this.image.getId();
    }

}
