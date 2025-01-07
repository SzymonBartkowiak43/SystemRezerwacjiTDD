package com.example.systemrezerwacji.domain.salonModule.dto;

import com.example.systemrezerwacji.domain.openingHoursModule.OpeningHours;
import jakarta.annotation.Nullable;

import java.util.List;

public record AddHoursResponseDto(String message, @Nullable List<OpeningHours> openingHours) {
}
