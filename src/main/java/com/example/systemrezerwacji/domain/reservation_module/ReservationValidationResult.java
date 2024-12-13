package com.example.systemrezerwacji.domain.reservation_module;


import jakarta.annotation.Nullable;

record ReservationValidationResult(boolean isValid, @Nullable String message) {
    static final String SUCCESS_MESSAGE = "success";

    static ReservationValidationResult success() {
        return new ReservationValidationResult(true, SUCCESS_MESSAGE);
    }

    static ReservationValidationResult failure(String message) {
        return new ReservationValidationResult(false, message);
    }
}
