package com.example.api.services;

import com.example.api.entities.Image;
import com.example.api.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
    Image findById(Long id);
    Image findByName(String name);
    void deleteById(Long id);
    Image save(MultipartFile file);
}
