package com.example.systemrezerwacji.domain.offerModule.exception;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(String message) {
        super(message);
    }
}
