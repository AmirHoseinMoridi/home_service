package com.example.home_service.exception;

public class DutyOutOfRangeException extends RuntimeException{
    public DutyOutOfRangeException() {
    }

    public DutyOutOfRangeException(String message) {
        super(message);
    }
}
