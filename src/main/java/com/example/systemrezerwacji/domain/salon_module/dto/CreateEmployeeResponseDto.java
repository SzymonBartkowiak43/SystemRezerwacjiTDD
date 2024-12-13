package com.example.systemrezerwacji.domain.salon_module.dto;

import jakarta.annotation.Nullable;

public record CreateEmployeeResponseDto(String message, @Nullable String employeeEmail, @Nullable String employeePassword) {
}
