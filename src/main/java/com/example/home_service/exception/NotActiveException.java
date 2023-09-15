package com.example.home_service.exception;

public class NotActiveException
        extends RuntimeException {
    public NotActiveException() {
    }

    public NotActiveException(String message) {
        super(message);
    }
}
