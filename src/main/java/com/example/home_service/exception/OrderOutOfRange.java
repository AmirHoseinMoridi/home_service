package com.example.home_service.exception;

public class OrderOutOfRange
        extends RuntimeException {
    public OrderOutOfRange() {
    }

    public OrderOutOfRange(String message) {
        super(message);
    }
}
