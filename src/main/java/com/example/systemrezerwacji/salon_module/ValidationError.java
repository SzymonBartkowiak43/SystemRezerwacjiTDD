package com.example.systemrezerwacji.salon_module;

enum ValidationError {

    EMPTY_NAME("Salon name shouldn't be null"),
    EMPTY_CATEGORY("Salon name shouldn't be null"),
    EMPTY_CITY("Salon name shouldn't be null"),
    EMPTY_ZIP_CODE("Salon name shouldn't be null"),
    EMPTY_STREET("Salon name shouldn't be null"),
    EMPTY_NUMBER("Salon name shouldn't be null");


    final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
