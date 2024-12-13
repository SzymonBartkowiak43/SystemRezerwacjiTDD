package com.example.systemrezerwacji.domain.salon_module.exception;

public class SalonNotFoundException extends RuntimeException {
    public SalonNotFoundException(String message) {
        super(message);
    }
}
