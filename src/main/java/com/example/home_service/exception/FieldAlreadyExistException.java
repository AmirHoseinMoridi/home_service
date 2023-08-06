package com.example.home_service.exception;

public class FieldAlreadyExistException extends Exception{
    public FieldAlreadyExistException() {
    }

    public FieldAlreadyExistException(String message) {
        super(message);
    }
}
