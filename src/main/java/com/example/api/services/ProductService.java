package com.example.api.services;

import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.repositories.ProductRepository;
import com.example.api.repositories.ProductTypeRepository;
import com.example.api.services.props.PageSizeProps;
import com.example.api.services.props.SortBy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository typeRepository;
    private final PageSizeProps pageSizeProps;
    private Sort sort;
    private boolean trigger = true;

    @Cacheable
    public Page<Product> getAllProducts(Pageable page){
        return productRepository.findAll(page);
//        return productRepository.findAll();
    }


//    Поиск по названию
    public List<Product> getListProducts(String title){
        return productRepository.findAllByTitleIgnoreCaseStartsWith(title);
    }

    @Transactional
    public void saveOrUpdateProduct(@Valid Product product, MultipartFile file) throws IOException {
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
        List<String> imageType = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif");
        String originalFileName = file.getOriginalFilename();
        String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        if (imageType.contains(fileSuffix)){
            Image image = Image.builder()
                    .name(System.currentTimeMillis() + "." + fileSuffix)
                    .originalFileName(file.getName())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .bytes(file.getBytes())
                    .build();
            return image;
        }
        return null;
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

//    =======================================

    @Cacheable
    public Page<Product> findByPagination(int pageNo) {
        if (sort == null){
            sort = Sort.by("id").descending();
        }
        // TODO Auto-generated method stub
        int pageSize = pageSizeProps.getPageSize(); // How many records on per page
//        System.out.println("Sort name = : " + sort);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    public void filterBy(SortBy sort) {
        switch (sort){
            case IDASC:
                this.sort = Sort.by("id").ascending();
                trigger = false;
                break;
            case IDDESC:
                this.sort = Sort.by("id").descending();
                trigger = true;
                break;
            case TITLEASC:
                this.sort = Sort.by("title").ascending();
                trigger = false;
                break;
            case TITLEDESC:
                this.sort = Sort.by("title").descending();
                trigger = true;
                break;
            case PRICEASC:
                this.sort = Sort.by("price").ascending();
                trigger = false;
                break;
            case PRICEDESC:
                this.sort = Sort.by("price").descending();
                trigger = true;
                break;
            case CREATEDATEASC:
                this.sort = Sort.by("createdAt").ascending();
                trigger = false;
                break;
            case CREATEDATEDESC:
                this.sort = Sort.by("createdAt").descending();
                trigger = true;
                break;
            default:
                this.sort = Sort.by("id").descending();
        }
    }

    public boolean getTrigger() {
        if (trigger){
            trigger = true;
        } else {
            trigger = false;
        }
        return trigger;
    }
}
