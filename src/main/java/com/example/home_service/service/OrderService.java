package com.example.home_service.service;


import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.dto.OrderDto;
import com.example.home_service.entity.Order;

import java.util.Optional;
import java.util.Set;

public interface OrderService {

    Order addOrder(OrderDto orderDTO) ;

    Set<Order> findOrders(EmailAndPasswordDto emailAndPasswordDto);

    Optional<Order> findById(Long id);
    void update(Order order);

    Order start(Long orderId);
    Order done(Long orderId);
}
