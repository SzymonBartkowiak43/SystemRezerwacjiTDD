package com.example.systemrezerwacji.domain.offer_module.exception;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(String message) {
        super(message);
    }
}
