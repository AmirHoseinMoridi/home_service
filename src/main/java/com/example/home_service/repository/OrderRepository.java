package com.example.home_service.repository;

import com.example.home_service.entity.Comment;
import com.example.home_service.entity.Customer;
import com.example.home_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findByCustomer(Customer customer);

    Optional<Order> findByComment(Comment comment);




}
