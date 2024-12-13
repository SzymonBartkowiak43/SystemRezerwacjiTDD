package com.example.systemrezerwacji.domain.salon_module.dto;

import com.example.systemrezerwacji.domain.opening_hours_module.OpeningHours;
import jakarta.annotation.Nullable;

import java.util.List;

public record AddHoursResponseDto(String message, @Nullable List<OpeningHours> openingHours) {
}
