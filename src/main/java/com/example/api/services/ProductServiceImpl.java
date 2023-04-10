package com.example.api.services;


import com.example.api.dto.ProductDto;
import com.example.api.dto.UserDto;
import com.example.api.entities.Cart;
import com.example.api.entities.Image;
import com.example.api.entities.Product;
import com.example.api.mapper.ProductMapper;
import com.example.api.repositories.ProductRepository;
import com.example.api.services.props.PageSizeProps;
import com.example.api.services.props.SortBy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PageSizeProps pageSizeProps;
    private boolean trigger = true; // Used by for Sorting
    private final ProductMapper productMapper = ProductMapper.PRODUCT_MAPPER;
    private SortBy sortBy;

    private final ImageService imageService;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              PageSizeProps pageSizeProps,
                              ImageService imageService,
                              UserService userService,
                              CartService cartService) {
        this.productRepository = productRepository;
        this.pageSizeProps = pageSizeProps;
        this.imageService = imageService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
//    @Cacheable
    public Page<ProductDto> getAllProducts() {
        List<ProductDto> products = productMapper.fromProductList(productRepository.findAll());
        Page<ProductDto> page = convertProductDtoToPage(products, 0, 30);
        return page;
    }

    @Override
    public ProductDto getProductById(Long id){
        Product product = productRepository.findById(id).orElse(null);
        return productMapper.fromProduct(product);
    }


    @Transactional(readOnly = true)
    @Override
    public List<ProductDto> findByTitle(String title) {
        List<Product> productList = productRepository.findAllByTitleIgnoreCaseStartsWith(title);
        return productMapper.fromProductList(productList);
    }

    @Cacheable
    @Override
    public Page<ProductDto> findByPagination(int pageNo) {
        List<ProductDto> products = productMapper.fromProductList(productRepository.findAll());
        if (sortBy == null){
            sortBy = SortBy.IDASC;
        }  else {
            switch (sortBy){
                case IDDESC:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getId).reversed()).collect(Collectors.toList());
                    break;
                case TITLEASC:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getTitle)).collect(Collectors.toList());
                    break;
                case TITLEDESC:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getTitle).reversed()).collect(Collectors.toList());
                    break;
                case PRICEASC:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getPrice)).collect(Collectors.toList());
                    break;
                case PRICEDESC:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getPrice).reversed()).collect(Collectors.toList());
                    break;
                case CREATEDATEASC:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getCreatedAt)).collect(Collectors.toList());
                    break;
                case CREATEDATEDESC:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getCreatedAt).reversed()).collect(Collectors.toList());
                    break;
                default:
                    products = products.stream().sorted(Comparator.comparing(ProductDto::getId)).collect(Collectors.toList());
                    break;
            }
        }
        return convertProductDtoToPage(products,pageNo - 1, pageSizeProps.getPageSize());
    }

    private Page<ProductDto> convertProductDtoToPage(List<ProductDto> products,int startPage, int endPage) {
        Pageable pageable = PageRequest.of(startPage, endPage);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((startPage + pageable.getPageSize()), products.size());
        return new PageImpl<>(products.subList(startPage, end), pageable, products.size());
    }

    @Transactional
    @Override
    public void addProductToUserCart(Long productId, String username) {
        UserDto userDto = userService.findByEmail(username);
        if (userDto == null){
            throw new RuntimeException("User not found : " + username);
        }
        Cart cart = userDto.getCart();

        if (cart != null){
            cartService.addProducts(userDto, getProductById(productId));
        } else {
            Cart newCart = cartService.createCart();
            userDto.setCart(newCart);
            userService.save(userDto);
        }
    }

//    @Transactional
    @Override
    public void saveOrUpdateProduct(@Valid ProductDto productDto, MultipartFile file, String typeWeightDetail){
        Image img;
        if (productDto.getId() == null){
            productDto.setImage(imageService.save(file));
            String weightDetail = productDto.getWeightAndPiece() + " " + typeWeightDetail;
            productDto.setWeightAndPiece(weightDetail);
            Product product = productMapper.toProduct(productDto);
            productRepository.save(product);
        }
        else if(!file.isEmpty()) {
            if (productDto.getImage() != null){
                ProductDto changedProduct = changeWeightDetailFromProduct(productDto, typeWeightDetail);
                Image image = productDto.getImage();
                img = imageService.save(file);
                changedProduct.setImage(img);
                productRepository.save(productMapper.toProduct(changedProduct));
                imageService.deleteById(image.getId());
            }

        }
        else {
            ProductDto changedProductWeight = changeWeightDetailFromProduct(productDto, typeWeightDetail);
            productRepository.save(productMapper.toProduct(changedProductWeight));
        }
    }

    private ProductDto changeWeightDetailFromProduct(ProductDto productDto, String typeWeightDetail){
        StringBuilder sb = new StringBuilder();
        sb.append(productDto.getWeightAndPiece().replaceAll("[^0-9]+", ""));
        sb.append(" ").append(typeWeightDetail);
        productDto.setWeightAndPiece(sb.toString());
        return productDto;
    }

    @Override
    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

//    /**
//     *
//     * @param file Принимает MultiPartFile.class
//     * @return Image.class
//     * @throws IOException
//     */
//    private Image toImageEntity(MultipartFile file) throws IOException {
//        List<String> imageType = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif");
//        String originalFileName = file.getOriginalFilename();
//        String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
//        if (imageType.contains(fileSuffix)){
//            Image image = Image.builder()
//                    .name(System.currentTimeMillis() + "." + fileSuffix)
//                    .originalFileName(file.getName())
//                    .contentType(file.getContentType())
//                    .size(file.getSize())
//                    .bytes(file.getBytes())
//                    .build();
//            return image;
//        }
//        return null;
//    }
    @Override
    public void filterBy(SortBy sort) {
        switch (sort){
            case IDASC:
                trigger = false;
                sortBy = SortBy.IDASC;
                break;
            case IDDESC:
                trigger = true;
                sortBy = SortBy.IDDESC;
                break;
            case TITLEASC:
                trigger = false;
                sortBy = SortBy.TITLEASC;
                break;
            case TITLEDESC:
                trigger = true;
                sortBy = SortBy.TITLEDESC;
                break;
            case PRICEASC:
                trigger = false;
                sortBy = SortBy.PRICEASC;
                break;
            case PRICEDESC:
                trigger = true;
                sortBy = SortBy.PRICEDESC;
                break;
            case CREATEDATEASC:
                trigger = false;
                sortBy = SortBy.CREATEDATEASC;
                break;
            case CREATEDATEDESC:
                trigger = true;
                sortBy = SortBy.CREATEDATEDESC;
                break;
            default:
                sortBy = SortBy.IDASC;
                break;
        }
    }
    @Override
    public boolean getTrigger() {
        return trigger = trigger;
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

}
