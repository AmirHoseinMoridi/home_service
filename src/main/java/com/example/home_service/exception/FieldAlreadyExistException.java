package com.example.home_service.exception;

public class FieldAlreadyExistException extends RuntimeException{
    public FieldAlreadyExistException() {
    }

    public FieldAlreadyExistException(String message) {
        super(message);
    }
}
