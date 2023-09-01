package com.example.home_service.service.impl;

import com.example.home_service.dto.CommentDto;
import com.example.home_service.dto.OrderDto;
import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.OrderStatus;
import com.example.home_service.entity.enumaration.ProposalStatus;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.mapper.Mapper;
import com.example.home_service.repository.OrderRepository;
import com.example.home_service.service.OrderService;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
    public void addOrder(OrderDto orderDTO) {


        Customer customer = serviceRegistry.customerService()
                .findByEmail(orderDTO.getCustomerEmail());
        SubDuty subDuty = serviceRegistry.subDutyService()
                .findByName(orderDTO.getSubDutyName());
        Address address = serviceRegistry.addressService().save(
                mapper.dtoToAddress(orderDTO.getAddressDto()));

        Order order = Order.builder()
                .description(orderDTO.getDescription())
                .suggestedPriceByCustomer(orderDTO.getSuggestedPriceByCustomer())
                .suggestedDateForStartWork(orderDTO.getSuggestedDateForStartWork())
                .status(OrderStatus.WAITING_FOR_EXPERT_ADVICE)
                .customer(customer)
                .subDuty(subDuty)
                .address(address)
                .build();
         repository.save(order);

    }

    @Override
    public Set<Order> findOrders(String email, String password) {
        Expert expert = serviceRegistry.expertService()
                .findByEmailAndPassword(email, password);

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


        Proposal proposal = serviceRegistry.proposalService().findAcceptedByOrder(order);

        LocalDateTime proposalTime = proposal.getSuggestedDate()
                .plusHours(proposal.getDurationOfWork().getHour())
                .plusMinutes(proposal.getDurationOfWork().getMinute());

        int subtraction = 0;
        if (proposalTime.isAfter(order.getSuggestedDateForStartWork())){
            subtraction+=proposalTime.getHour()-order.getSuggestedDateForStartWork().getHour();
        }
        serviceRegistry.expertService().subtractPoint(proposal.getExpert(),subtraction);
    }
}
