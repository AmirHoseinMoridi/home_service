package com.example.home_service.service;


import com.example.home_service.dto.CommentDto;
import com.example.home_service.entity.Comment;
import com.example.home_service.entity.Order;

public interface CommentService {
    Comment addComment(Order order, Integer point, String description);
}
