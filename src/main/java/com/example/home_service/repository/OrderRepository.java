package com.example.home_service.repository;

import com.example.home_service.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findByCustomer(Customer customer);

    @Query(value = "select o from Order o where o.customer = :customer and o.subDuty = :subDuty " +
            "and (o.status = 'WAITING_FOR_EXPERT_ADVICE' or o.status = 'WAITING_FOR_EXPERT_SELECTION') ")
    Set<Order> findByCustomerAndSubDuty(@Param(value = "") Customer customer, SubDuty subDuty);

    @Query(value = "select o from Order o where :expert member of o.subDuty.experts" +
            " and (o.status = 'WAITING_FOR_EXPERT_ADVICE' or o.status = 'WAITING_FOR_EXPERT_SELECTION')")
    Set<Order> findByExpert(@Param(value = "expert") Expert expert);

}
