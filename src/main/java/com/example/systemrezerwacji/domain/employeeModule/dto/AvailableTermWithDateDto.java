package com.example.systemrezerwacji.domain.employeeModule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AvailableTermWithDateDto(LocalTime startServices, LocalTime endServices, LocalDate date) {
}
