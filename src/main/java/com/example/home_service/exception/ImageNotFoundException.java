package com.example.home_service.exception;

import java.io.IOException;

public class ImageNotFoundException extends IOException {
    public ImageNotFoundException() {
    }

    public ImageNotFoundException(String message) {
        super(message);
    }
}
