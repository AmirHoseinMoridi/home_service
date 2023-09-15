package com.example.home_service.service.impl;

import com.example.home_service.dto.CommentDto;
import com.example.home_service.entity.Comment;
import com.example.home_service.entity.Order;
import com.example.home_service.exception.FieldNotFoundException;
import com.example.home_service.repository.CommentRepository;
import com.example.home_service.service.CommentService;
import com.example.home_service.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;


    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment addComment(Order order,Integer point,String description) {
        Comment comment = Comment.builder()
                .order(order)
                .point(point)
                .description(description)
                .build();
        return repository.save(comment);
    }
}
