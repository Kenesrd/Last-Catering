package com.example.api.services;

import com.example.api.dto.CartDto;
import com.example.api.dto.ProductDto;
import com.example.api.dto.UserDto;
import com.example.api.entities.Cart;
import com.example.api.entities.Product;
import com.example.api.entities.User;

import java.util.List;

public interface CartService {
    Cart createCart();
    void addProducts(UserDto userDto, ProductDto productDto);
    Cart findByUserEmail(String email);
    Cart findCartById(Long id);
    CartDto findCartByUser(UserDto userDto);
    void commitCartToOrder(String userName);

    void saveProduct(ProductDto productDto);
}
