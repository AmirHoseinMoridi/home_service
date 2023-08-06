package com.example.home_service.repository;


import com.example.home_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //todo faze 2
}
