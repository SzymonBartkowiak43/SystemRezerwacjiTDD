package com.example.systemrezerwacji.reservation_module.dto;


import jakarta.annotation.Nullable;

public record ReservationValidationResult(boolean isValid, @Nullable String message) {
}
