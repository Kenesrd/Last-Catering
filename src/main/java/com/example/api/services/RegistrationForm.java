package com.example.api.services;

import com.example.api.dto.UserDto;
import com.example.api.entities.Cart;
import com.example.api.entities.Role;
import com.example.api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String surname;
    private String phone;
    private String email;
    private String password;

    public UserDto toUserDto(PasswordEncoder passwordEncoder){
        return new UserDto(username,surname,phone,email,passwordEncoder.encode(password),Role.ADMIN, new Cart());
    }

}
