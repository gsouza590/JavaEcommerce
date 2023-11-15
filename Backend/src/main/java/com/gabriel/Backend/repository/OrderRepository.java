package com.gabriel.Backend.repository;

import com.gabriel.Backend.model.City;
import com.gabriel.Backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
