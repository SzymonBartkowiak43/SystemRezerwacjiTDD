package com.example.systemrezerwacji.reservation_module.dto;

import jakarta.annotation.Nullable;

public record ReservationFacadeResponse(boolean isSuccess, @Nullable String message) {
}
