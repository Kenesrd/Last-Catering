package com.example.api.dto;

import com.example.api.entities.Image;
import com.example.api.entities.ProductType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank(message = "Название продукта Не должно быть пустым!")
    @Size(message = "Минимальная длина символа должна быть 3", min = 3)
    private String title;
    @NotBlank(message = "Описание продукта Не должно быть пустым!")
    @Size(message = "Минимальная длина символа должна быть 10", min = 10)
    private String description;
    @NotBlank(message = "Вы не написали вес продукта!")
    private String weightAndPiece;
    @Positive
    @NotNull(message = "Цена продукта Не должно быть пустым или отрицательным!")
    private BigDecimal price;
    private Date createdAt = new Date();
    private Image image;
    @NotNull(message = "Вы не выбрали тип продукта!")
    private ProductType productType;
}
