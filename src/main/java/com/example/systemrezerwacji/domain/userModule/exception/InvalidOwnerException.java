package com.example.systemrezerwacji.domain.userModule.exception;

public class InvalidOwnerException extends RuntimeException{
    public InvalidOwnerException(String message) {
        super(message);
    }
}
