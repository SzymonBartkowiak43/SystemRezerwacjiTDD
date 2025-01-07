package com.example.systemrezerwacji.domain.employeeModule.exception;

public class EmployeeDuplicateOfferException extends RuntimeException{

    public EmployeeDuplicateOfferException() {
        super("Employee already have assign this offer!");
    }
}
