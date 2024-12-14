package com.example.systemrezerwacji.domain.salon_module.dto;

import jakarta.annotation.Nullable;

public record SalonFacadeResponseDto(String message, @Nullable Long salonId) {
}
