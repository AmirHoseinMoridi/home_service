package com.example.home_service.exception;

public class WrongActionException extends Exception{
    public WrongActionException() {
    }

    public WrongActionException(String message) {
        super(message);
    }
}
