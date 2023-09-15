package com.example.home_service.service;


import com.example.home_service.entity.Order;

public interface CommentService {
    void addComment(Order order, Integer point, String description);
}
