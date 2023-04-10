package com.example.api.dto;

import com.example.api.entities.Cart;
import com.example.api.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private Role role;
    private Cart cart;
}
