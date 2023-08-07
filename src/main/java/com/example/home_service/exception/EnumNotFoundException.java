package com.example.home_service.exception;

public class EnumNotFoundException extends RuntimeException {
    public EnumNotFoundException() {
    }

    public EnumNotFoundException(String message) {
        super(message);
    }
}
