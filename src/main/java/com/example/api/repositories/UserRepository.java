package com.example.api.repositories;

import com.example.api.entities.Role;
import com.example.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);
}
