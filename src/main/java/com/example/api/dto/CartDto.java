package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private int amountProducts;
    private Double sum;
    private List<CartDetailDto> cartDetails = new ArrayList<>();

    public void aggregate(){
        this.amountProducts = cartDetails.size();
        this.sum = cartDetails.stream()
                .map(CartDetailDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
