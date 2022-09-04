package com.example.api.services;

import com.example.api.entities.Image;
import com.example.api.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Cacheable
    public Image findById(Long id){
        return imageRepository.findById(id).orElse(null);
    }
}
