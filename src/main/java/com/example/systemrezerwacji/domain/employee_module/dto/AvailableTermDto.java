package com.example.systemrezerwacji.domain.employee_module.dto;

import java.time.LocalTime;

public record AvailableTermDto(LocalTime startServices, LocalTime endServices ) {
}
