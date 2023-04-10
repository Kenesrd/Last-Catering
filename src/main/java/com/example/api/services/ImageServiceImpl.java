package com.example.api.services;

import com.example.api.entities.Image;
import com.example.api.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    @Autowired
    public void setImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Cacheable
    @Override
    public Image findById(Long id){
        return imageRepository.findById(id).orElse(null);
    }

    @Cacheable
    @Override
    public Image findByName(String name) {
        return imageRepository.findByName(name);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Image save(MultipartFile file){
        List<String> imageType = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif");
        String originalFileName = file.getOriginalFilename();
        String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        if (imageType.contains(fileSuffix)){
            Image image = null;
            try {
                image = Image.builder()
                        .name(System.currentTimeMillis() + "." + fileSuffix)
                        .originalFileName(file.getName())
                        .contentType(file.getContentType())
                        .size(file.getSize())
                        .bytes(file.getBytes())
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageRepository.save(image);
        }
        return null;
    }
}
