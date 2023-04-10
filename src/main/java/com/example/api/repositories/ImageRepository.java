package com.example.api.repositories;

import com.example.api.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByName(String name);
    Optional<Image> findById(Long id);
    void deleteById(Long id);
}
