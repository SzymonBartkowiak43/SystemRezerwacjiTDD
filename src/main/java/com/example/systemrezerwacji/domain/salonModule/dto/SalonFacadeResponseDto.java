package com.example.systemrezerwacji.domain.salonModule.dto;

import jakarta.annotation.Nullable;

public record SalonFacadeResponseDto(String message, @Nullable Long salonId) {
}
