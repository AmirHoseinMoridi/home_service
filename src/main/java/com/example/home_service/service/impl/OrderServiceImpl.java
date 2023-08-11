package com.example.home_service.service.impl;

import com.example.home_service.dto.EmailAndPasswordDto;
import com.example.home_service.dto.OrderDto;
import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.OrderStatus;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.OrderRepository;
import com.example.home_service.service.OrderService;
import com.example.home_service.service.ServiceRegistry;
import com.example.home_service.util.Checker;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Component
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ServiceRegistry serviceRegistry;
    private final Mapper mapper;
    @Autowired
    public OrderServiceImpl(OrderRepository repository, ServiceRegistry serviceRegistry, Mapper mapper) {
        this.repository = repository;
        this.serviceRegistry = serviceRegistry;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Order addOrder(OrderDto orderDTO) {

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
                        .status(OrderStatus.WAITING_FOR_EXPERT_ADVICE)
                        .customer(customer)
                        .subDuty(subDuty)
                        .address(address)
                        .comment(comment)
                        .build();
                return repository.save(order);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Order();
        }
    }

    @Override
    public Set<Order> findOrders(EmailAndPasswordDto emailAndPasswordDto) {

        Expert expert = serviceRegistry.expertService()
                .findByEmailAndPassword(emailAndPasswordDto);
        return repository.findByExpert(expert);
    }



    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void update(Order order) {
        repository.save(order);
    }

    @Override
    public Order start(Long orderId) {
        Optional<Order> optional = repository.findById(orderId);
        if (optional.isEmpty()){
            throw new FieldNotFoundException("order is not exists !");
        }
        Order order = optional.get();
        order.setStatus(OrderStatus.STARTED);
        return order;
    }

    @Override
    public Order done(Long orderId) {
        Optional<Order> optional = repository.findById(orderId);
        if (optional.isEmpty()){
            throw new FieldNotFoundException("order is not exists !");
        }
        Order order = optional.get();
        order.setStatus(OrderStatus.DONE);
        return order;
    }
}
