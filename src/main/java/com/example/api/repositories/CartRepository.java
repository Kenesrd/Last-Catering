package com.example.api.repositories;

import com.example.api.entities.Cart;
import com.example.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartById(Cart cart);
    Cart findByUser_Email(String email);
    Cart findCartByUser(User user);
}
