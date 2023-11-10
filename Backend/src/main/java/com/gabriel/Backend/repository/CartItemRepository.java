package com.gabriel.Backend.repository;

import com.gabriel.Backend.model.CartItem;
import com.gabriel.Backend.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
