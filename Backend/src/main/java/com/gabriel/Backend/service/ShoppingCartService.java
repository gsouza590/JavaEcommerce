package com.gabriel.Backend.service;

import com.gabriel.Backend.dto.ProductDto;
import com.gabriel.Backend.model.ShoppingCart;
import org.springframework.stereotype.Service;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);

}
