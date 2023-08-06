package com.example.home_service.exception;

public class DutyOutOfRangeException extends Exception{
    public DutyOutOfRangeException() {
    }

    public DutyOutOfRangeException(String message) {
        super(message);
    }
}
