package com.example.home_service.exception;

public class FieldNotFoundException
        extends RuntimeException{
    public FieldNotFoundException() {
    }

    public FieldNotFoundException(String message) {
        super(message);
    }
}
