package com.example.home_service.exception;

public class NotActiveException extends Exception {
    public NotActiveException() {
    }

    public NotActiveException(String message) {
        super(message);
    }
}
