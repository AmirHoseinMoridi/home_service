package com.example.home_service.service.impl;

import com.example.home_service.dto.OrderDto;
import com.example.home_service.entity.*;
import com.example.home_service.repository.OrderRepository;
import com.example.home_service.service.OrderService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ServiceRegistry serviceRegistry;
    @Autowired
    public OrderServiceImpl(OrderRepository repository, ServiceRegistry serviceRegistry) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
    }

    @Transactional
    @Override
    public void addOrder(OrderDto orderDTO) {

        try {
            Checker.checkValidation(orderDTO);

            Customer customer = serviceRegistry.customerService()
                    .findByEmail(orderDTO.getCustomerEmail());
            SubDuty subDuty = serviceRegistry.subDutyService()
                    .findByName(orderDTO.getSubDutyName());
            Address address = serviceRegistry.addressService().save(orderDTO.getAddressDto());
            Comment comment = serviceRegistry.commentService().createDefaultComment();

                Order order = Order.builder()
                        .description(orderDTO.getDescription())
                        .suggestedPriceByCustomer(orderDTO.getSuggestedPriceByCustomer())
                        .suggestedDateForStartWork(orderDTO.getSuggestedDateForStartWork())
                        .customer(customer)
                        .subDuty(subDuty)
                        .address(address)
                        .comment(comment)
                        .build();
                repository.save(order);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
