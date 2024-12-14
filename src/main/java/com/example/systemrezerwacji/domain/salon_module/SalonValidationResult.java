package com.example.systemrezerwacji.domain.salon_module;

record SalonValidationResult(String validationMessage) {

    static final String SUCCESS_MESSAGE = "success";

    static SalonValidationResult success() {
        return new SalonValidationResult(SUCCESS_MESSAGE);
    }

    static SalonValidationResult failure(String message) {
        return new SalonValidationResult(message);
    }

}
