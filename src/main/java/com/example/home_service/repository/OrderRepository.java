package com.example.home_service.repository;

import com.example.home_service.entity.*;
import com.example.home_service.entity.enumaration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Set;

public interface OrderRepository
        extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    Set<Order> findByCustomer(Customer customer);

    @Query(value = "select o from Order o where :expert member of o.subDuty.experts" +
            " and (o.status = 'WAITING_FOR_EXPERT_ADVICE' or o.status = 'WAITING_FOR_EXPERT_SELECTION')")
    Set<Order> findByExpert(@Param(value = "expert") Expert expert);


    Set<Order> findByStatus(OrderStatus status);
    Set<Order> findBySuggestedDateForStartWorkBetween(ZonedDateTime start, ZonedDateTime end);

    Set<Order> findBySubDutyAndStatus(SubDuty subDuty, OrderStatus status);
    Set<Order> findBySubDuty(SubDuty subDuty);



}
