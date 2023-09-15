package com.example.home_service.repository;


import com.example.home_service.entity.Comment;
import com.example.home_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository
        extends JpaRepository<Comment,Long> {

    Optional<Comment> findByOrder(Order order);

}
