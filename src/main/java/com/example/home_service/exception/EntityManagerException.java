package com.example.home_service.exception;

public class EntityManagerException
        extends RuntimeException {
    public EntityManagerException() {
    }

    public EntityManagerException(String message) {
        super(message);
    }
}
