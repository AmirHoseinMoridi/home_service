package com.example.home_service.exception;

public class InsufficientInventoryException
        extends RuntimeException{
    public InsufficientInventoryException() {
    }

    public InsufficientInventoryException(String message) {
        super(message);
    }
}
