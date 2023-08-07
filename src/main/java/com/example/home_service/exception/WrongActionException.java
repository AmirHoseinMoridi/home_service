package com.example.home_service.exception;

public class WrongActionException extends RuntimeException{
    public WrongActionException() {
    }

    public WrongActionException(String message) {
        super(message);
    }
}
