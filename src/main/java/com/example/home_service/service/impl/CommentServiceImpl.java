package com.example.home_service.service.impl;

import com.example.home_service.entity.Comment;
import com.example.home_service.entity.Order;
import com.example.home_service.repository.CommentRepository;
import com.example.home_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommentServiceImpl
        implements CommentService {

    private final CommentRepository repository;


    @Override
    public void addComment(Order order, Integer point, String description) {
        Comment comment = Comment.builder()
                .order(order)
                .point(point)
                .description(description)
                .build();
        repository.save(comment);
    }
}
