package com.example.systemrezerwacji.salon_module.dto;

import jakarta.annotation.Nullable;

public record SalonFacadeResponseDto(String message, @Nullable Long salonId) {
}
