package com.example.home_service.exception;

public class NotValidException extends RuntimeException {
    public NotValidException() {
    }

    public NotValidException(String message) {
        super(message);
    }


}
