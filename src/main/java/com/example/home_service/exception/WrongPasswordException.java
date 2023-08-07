package com.example.home_service.exception;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
