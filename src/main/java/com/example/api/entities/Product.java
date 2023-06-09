package com.example.api.entities;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
/*

 */


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "products")
@EqualsAndHashCode
public class Product implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "prodSeq", sequenceName = "prod_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodSeq")
    private Long id;
    @NotBlank(message = "Название продукта Не должно быть пустым!")
    @Size(message = "Минимальная длина символа должна быть 3", min = 3)
    private String title;
    @NotBlank(message = "Описание продукта Не должно быть пустым!")
    @Size(message = "Минимальная длина символа должна быть 10", min = 10)
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @NotBlank(message = "Вы не написали вес продукта!")
    private String weightAndPiece;
    @Positive
    @NotNull(message = "Цена продукта Не должно быть пустым или отрицательным!")
    private BigDecimal price;
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    @JoinTable(name = "img_prod",
//                joinColumns = @JoinColumn(name = "prod_id"),
//                inverseJoinColumns = @JoinColumn(name="img_id"))
    private Image image;
    @NotNull(message = "Вы не быбрали тип продукта!")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private ProductType productType;
}
