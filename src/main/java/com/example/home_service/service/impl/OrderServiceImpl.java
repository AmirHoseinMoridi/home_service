package com.example.home_service.service.impl;

import com.example.home_service.dto.OrderDto;
import com.example.home_service.entity.*;
import com.example.home_service.repository.OrderRepository;
import com.example.home_service.service.OrderService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    @Transactional
    @Override
    public void addOrder(OrderDto orderDTO) {

        try {
            Checker.checkValidation(orderDTO);

            Customer customer = ServiceRegistry.customerService()
                    .findByEmail(orderDTO.getCustomerEmail());
            SubDuty subDuty = ServiceRegistry.subDutyService()
                    .findByName(orderDTO.getSubDutyName());
            Address address = ServiceRegistry.addressService().save(orderDTO.getAddressDto());
            Comment comment = ServiceRegistry.commentService().createDefaultComment();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
