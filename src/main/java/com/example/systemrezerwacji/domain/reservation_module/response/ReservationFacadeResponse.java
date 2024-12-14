package com.example.systemrezerwacji.domain.reservation_module.response;

import jakarta.annotation.Nullable;

public record ReservationFacadeResponse(boolean isSuccess, @Nullable String message) {
}
