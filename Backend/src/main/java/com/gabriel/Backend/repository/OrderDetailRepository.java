package com.gabriel.Backend.repository;

import com.gabriel.Backend.model.Order;
import com.gabriel.Backend.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("select o from Order o where o.customer.id = ?1")
    List<Order> findAllByCustomerId(Long id);
}
