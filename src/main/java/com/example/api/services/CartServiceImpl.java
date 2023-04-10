package com.example.api.services;

import com.example.api.dto.CartDetailDto;
import com.example.api.dto.CartDto;
import com.example.api.dto.ProductDto;
import com.example.api.dto.UserDto;
import com.example.api.entities.Cart;
import com.example.api.entities.Product;
import com.example.api.entities.User;
import com.example.api.mapper.ProductMapper;
import com.example.api.mapper.UserMapper;
import com.example.api.repositories.CartRepository;
import com.example.api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

//    private final ProductService productService;
    private final CartRepository cartRepository;
    private UserMapper userMapper = UserMapper.USER_MAPPER;
    private ProductMapper productMapper = ProductMapper.PRODUCT_MAPPER;

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    @Override
    public void addProducts(UserDto userDto, ProductDto productDto) {
        Cart cart = userDto.getCart();
        List<Product> products = cart.getProducts();
        Product product = productMapper.toProduct(productDto);
        products.add(product);
        cartRepository.save(cart);
    }

    @Override
    public Cart findByUserEmail(String email) {
        return cartRepository.findByUser_Email(email);
    }

//    @Override
//    public Cart findByUserDto(UserDto userdto) {
//        return cartRepository.findByUser_Email(userdto.getEmail());
//    }

    @Override
    public Cart findCartById(Long id) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public CartDto findCartByUser(UserDto userDto) {
        if (userDto == null || userDto.getCart() == null){
            return new CartDto();
        }
        CartDto cartDto = new CartDto();
        Map<Long, CartDetailDto> mapCartDetail = new HashMap<>();
        List<Product> products = userDto.getCart().getProducts();
        List<ProductDto> productDtos = productMapper.fromProductList(products);
        for (ProductDto product : productDtos){
            CartDetailDto cartDetailDto = mapCartDetail.get(product.getId());
            if (cartDetailDto == null){
                mapCartDetail.put(product.getId(), new CartDetailDto(product));
            } else {
                cartDetailDto.setProductId(product.getId());
                cartDetailDto.setAmount(cartDetailDto.getAmount().add(new BigDecimal(1.0)));  // Количество Товара
                cartDetailDto.setSum(cartDetailDto.getSum() + Double.valueOf(product.getPrice().toString())); // Сумма товара по количеству
            }
        }

        cartDto.setCartDetails(new ArrayList<>(mapCartDetail.values()));
        cartDto.aggregate();
        return cartDto;
    }

    @Override
    public void commitCartToOrder(String userName) {

    }

    @Override
    public void saveProduct(ProductDto productDto) {

    }
}
