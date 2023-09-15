package com.example.home_service.service.impl;

import com.example.home_service.dto.CommentDto;
import com.example.home_service.dto.OrderDto;
import com.example.home_service.dto.OrderSearchDto;
import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.OrderStatus;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.repository.OrderRepository;
import com.example.home_service.service.OrderService;
import com.example.home_service.service.ServiceRegistry;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;


@Component
@RequiredArgsConstructor
public class OrderServiceImpl
        implements OrderService {
    private final OrderRepository repository;
    private final ServiceRegistry serviceRegistry;


    @Transactional
    @Override
    public void addOrder(OrderDto orderDTO, String username) {


        Customer customer = serviceRegistry.customerService()
                .findByUsername(username)
                .orElseThrow(() -> new FieldNotFoundException("customer not found !"));
        SubDuty subDuty = serviceRegistry.subDutyService()
                .findByName(orderDTO.getSubDutyName());

        Order order = Order.builder()
                .description(orderDTO.getDescription())
                .suggestedPriceByCustomer(orderDTO.getSuggestedPriceByCustomer())
                .suggestedDateForStartWork(orderDTO.getSuggestedDateForStartWork())
                .status(OrderStatus.WAITING_FOR_EXPERT_ADVICE)
                .customer(customer)
                .subDuty(subDuty)
                .address(orderDTO.getAddress())
                .build();
        repository.save(order);

    }

    @Override
    public Set<Order> findOrders(String username) {
        Expert expert = serviceRegistry.expertService().findByUsername(username)
                .orElseThrow(() -> new FieldNotFoundException("expert not found !"));
        return repository.findByExpert(expert);
    }


    @Override
    public Order findById(Long id) {
        Optional<Order> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new FieldNotFoundException("order is not exists !");
        }
        return optional.get();
    }

    @Override
    public void update(Order order) {
        repository.save(order);
    }


    @Override
    public void start(Long orderId) {
        Order order = findById(orderId);
        order.setStatus(OrderStatus.STARTED);
        repository.save(order);
    }

    @Override
    public void done(CommentDto commentDto) {
        Order order = findById(commentDto.getOrderId());
        order.setStatus(OrderStatus.DONE);
        repository.save(order);
        serviceRegistry.commentService().addComment(order, commentDto.getPoint(), commentDto.getDescription());
    }

}
