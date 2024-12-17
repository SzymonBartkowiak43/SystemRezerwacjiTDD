package com.example.systemrezerwacji.domain.employee_module.exception;

public class EmployeeDuplicateOfferException extends RuntimeException{

    public EmployeeDuplicateOfferException() {
        super("Employee already have assign this offer!");
    }
}
