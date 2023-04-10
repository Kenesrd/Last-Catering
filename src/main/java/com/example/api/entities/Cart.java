package com.example.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "carts")
public class Cart implements Serializable {
    @Id
    @SequenceGenerator(name = "cartSeq",sequenceName = "cart_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartSeq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "cart_products",
                joinColumns = @JoinColumn(name = "cart_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
