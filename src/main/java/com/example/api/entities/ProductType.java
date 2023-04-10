package com.example.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "types_of_products")
@EqualsAndHashCode
public class ProductType implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "prodTypeSeq", sequenceName = "prod_type_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodTypeSeq")
    private Long id;

    @NotBlank(message = "Название типа Не должно быть пустым!")
    @Size(message = "Минимальная длина символа 3", min = 3)
    private String typeName;

    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
}
