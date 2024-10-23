package com.example.systemrezerwacji.salon_module;

record SalonValidationResult(String validationMessage) {

    public static final String SUCCESS_MESSAGE = "success";

    public static SalonValidationResult success() {
        return new SalonValidationResult(SUCCESS_MESSAGE);
    }

    public static SalonValidationResult failure(String message) {
        return new SalonValidationResult(message);
    }

}
