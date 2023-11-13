package com.gabriel.Backend.service;

import com.gabriel.Backend.model.Order;
import com.gabriel.Backend.model.ShoppingCart;

import java.util.List;

public interface OrderService {
    Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findAllOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);
}
