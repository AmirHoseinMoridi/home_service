package com.example.home_service.exception;

public class ImageSizeOutOfRangeException
        extends RuntimeException {
    public ImageSizeOutOfRangeException() {
    }

    public ImageSizeOutOfRangeException(String message) {
        super(message);
    }
}
