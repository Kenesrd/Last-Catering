package com.example.api.services;

import com.example.api.dto.UserDto;
import com.example.api.entities.Role;
import com.example.api.entities.User;
import com.example.api.mapper.UserMapper;
import com.example.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.USER_MAPPER;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.trim()).orElse(null);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        if (user == null){
            throw new UsernameNotFoundException("User '" + email + "' not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }

    @Cacheable
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userMapper.fromUserList(userRepository.findAll());
    }

    @Override
    public UserDto findByName(String name) {
        return userMapper.fromUser(userRepository.findByName(name).orElse(null));
    }


    @Override
    public UserDto findById(Long id) {
        return userMapper.fromUser(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserDto findByEmail(String email) {
        return userMapper.fromUser(userRepository.findByEmail(email).orElse(null));
    }

    @Transactional
    @Override
    public void save(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateProfile(UserDto userDto) {
        User savedUser = userRepository.findByEmail(userDto.getEmail()).orElse(null);

        if (savedUser == null){
            throw new RuntimeException("User not found by email " + userDto.getEmail());
        }

        boolean changed = false;

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            changed = true;
        }
        if (!Objects.equals(userDto.getName(), savedUser.getName())){
            savedUser.setName(userDto.getName());
            changed = true;
        }
        if (changed){
            userRepository.save(savedUser);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findUsersByRole(Role role) {
        return userMapper.fromUserList(userRepository.findAllByRole(role));
    }
}
