package com.example.home_service.service;


import com.example.home_service.dto.CommentDto;
import com.example.home_service.dto.OrderDto;
import com.example.home_service.dto.OrderSearchDto;
import com.example.home_service.entity.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {

    void addOrder(OrderDto orderDTO,String username) ;

    Set<Order> findOrders(String username);

    Order findById(Long id);

    void update(Order order);
    void start(Long orderId);
    void done(CommentDto commentDto);

}
