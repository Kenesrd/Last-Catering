package com.example.api.services;

import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.repositories.ProductRepository;
import com.example.api.repositories.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository typeRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
//    public List<Product> getAllProductsAsc(Sort sort){
//        return productRepository.findAllByIdOrderByIdAsc(sort);
//    }


    public List<Product> getListProducts(String title){
        return productRepository.findAllByTitleIgnoreCaseStartsWith(title);
    }

    public void saveOrUpdateProduct(Product product, MultipartFile file) throws IOException {
        Image img;
        img = toImageEntity(file);
        if (file.getSize() != 0){
            product.setImage(img);
        }
//        log.info("Saving new Product. Title: {}, ", product);

        productRepository.save(product);

    }

    /**
     *
     * @param file Принимает MultiPartFile.class
     * @return Image.class
     * @throws IOException
     */
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = Image.builder()
                .name(file.getName())
                .originalFileName(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .bytes(file.getBytes())
                .build();
        return image;
    }

    private void resizeFile(MultipartFile inputMultipartFile,MultipartFile outputMultipartFile, String imagePathToWrite,
                            int resizeWidth, int resizeHeight) throws IOException {
        BufferedImage bufferedImageInput = ImageIO.read(inputMultipartFile.getInputStream());

        BufferedImage bufferedImageOutput = new BufferedImage(resizeWidth,
                resizeHeight, bufferedImageInput.getType());

        Graphics2D g2d = bufferedImageOutput.createGraphics();
        g2d.drawImage(bufferedImageInput, 0, 0, resizeWidth, resizeHeight, null);
        g2d.dispose();

        String formatName = imagePathToWrite.substring(imagePathToWrite.lastIndexOf(".") + 1); // Получаем формат jpg

        ImageIO.write(bufferedImageOutput, formatName, new File(imagePathToWrite));
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
}
