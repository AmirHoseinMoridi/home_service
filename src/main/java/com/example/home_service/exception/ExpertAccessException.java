package com.example.home_service.exception;

public class ExpertAccessException extends RuntimeException{
    public ExpertAccessException() {
    }

    public ExpertAccessException(String message) {
        super(message);
    }
}
