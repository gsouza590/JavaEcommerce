package com.gabriel.Backend.dto;


import com.gabriel.Backend.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    private Long id;

    private CustomerDto customer;
    private BigDecimal totalPrice;
    private int totalItems;
    private Set<CartItemDto> cartItems;

}