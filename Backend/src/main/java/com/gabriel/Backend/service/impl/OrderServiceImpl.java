package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.model.CartItem;
import com.gabriel.Backend.model.Order;
import com.gabriel.Backend.model.OrderDetail;
import com.gabriel.Backend.model.ShoppingCart;
import com.gabriel.Backend.repository.CustomerRepository;
import com.gabriel.Backend.repository.OrderDetailRepository;
import com.gabriel.Backend.repository.OrderRepository;
import com.gabriel.Backend.service.OrderService;
import com.gabriel.Backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service


public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository detailRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShoppingCartService cartService;

    @Override
    @Transactional
    public Order save(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setAccept(false);
        order.setPaymentMethod("Cash");
        order.setOrderStatus("Pending");
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
    public List<Order> findAll(String username) {
        return null;
    }

    @Override
    public List<Order> findAllOrders() {
        return null;
    }

    @Override
    public Order acceptOrder(Long id) {
        return null;
    }

    @Override
    public void cancelOrder(Long id) {

    }
}

