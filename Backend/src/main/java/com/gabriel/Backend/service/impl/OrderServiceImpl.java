package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.exceptions.OrderNotFoundException;
import com.gabriel.Backend.model.*;
import com.gabriel.Backend.repository.CustomerRepository;
import com.gabriel.Backend.repository.OrderDetailRepository;
import com.gabriel.Backend.repository.OrderRepository;
import com.gabriel.Backend.service.OrderService;
import com.gabriel.Backend.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private static final String DEFAULT_PAYMENT_METHOD = "Dinheiro";
    private static final String DEFAULT_ORDER_STATUS = "Pendente";
    private static final int DEFAULT_DELIVERY_DAYS = 10;

    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartService cartService;

    @Override
    @Transactional
    public Order save(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setAccept(false);
        order.setPaymentMethod(DEFAULT_PAYMENT_METHOD);
        order.setOrderStatus(DEFAULT_ORDER_STATUS);
        order.setQuantity(shoppingCart.getTotalItems());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(item.getProduct());
            detailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }
        order.setOrderDetailList(orderDetailList);
        cartService.deleteCartById(shoppingCart.getId());
        return orderRepository.save(order);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll(String username) {
        log.info("Buscando todos os pedidos para o usuário: {}", username);
        Customer customer = customerRepository.findByUsername(username);
        return customer != null ? customer.getOrders() : Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        log.info("Buscando todos os pedidos");
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order acceptOrder(Long id) {
        log.info("Aceitando pedido ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found."));
        order.setAccept(true);
        order.setDeliveryDate(calculateDeliveryDate(DEFAULT_DELIVERY_DAYS));
        return orderRepository.save(order);
    }

    private Date calculateDeliveryDate(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return calendar.getTime();
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        log.info("Cancelando pedido ID: {}", id);
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found.");
        }
        orderRepository.deleteById(id);
    }
}
