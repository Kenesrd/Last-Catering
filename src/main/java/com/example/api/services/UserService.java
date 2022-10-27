package com.example.api.services;

import com.example.api.dto.UserDto;
import com.example.api.entities.Role;
import com.example.api.entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();
    UserDto findByName(String name);
    UserDto findById(Long id);
    UserDto findByEmail(String email);
    void save(UserDto userDto);
    void updateProfile(UserDto userDto);
    List<UserDto> findUsersByRole(Role role);
}
