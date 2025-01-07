package com.example.systemrezerwacji.domain.employeeModule.response;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record CreateEmployeeResponseDto(String message, @Nullable String employeeEmail, @Nullable String employeePassword) {
}
