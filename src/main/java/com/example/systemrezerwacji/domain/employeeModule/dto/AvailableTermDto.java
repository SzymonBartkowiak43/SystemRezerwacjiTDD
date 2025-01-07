package com.example.systemrezerwacji.domain.employeeModule.dto;

import java.time.LocalTime;

public record AvailableTermDto(LocalTime startServices, LocalTime endServices ) {
}
