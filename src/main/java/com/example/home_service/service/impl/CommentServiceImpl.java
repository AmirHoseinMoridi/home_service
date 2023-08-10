package com.example.home_service.service.impl;

import com.example.home_service.entity.Comment;
import com.example.home_service.repository.CommentRepository;
import com.example.home_service.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    public Comment createDefaultComment() {

        Comment comment = Comment.builder()
                .point(0)
                .description(Comment.DEFAULT_DESCRIPTION)
                .isRegistered(false)
                .build();
        return repository.save(comment);
    }
}
