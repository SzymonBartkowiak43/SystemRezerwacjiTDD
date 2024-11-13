package com.example.systemrezerwacji.employee_module.dto;

import java.time.LocalTime;

public record AvailableTermDto(LocalTime startServices, LocalTime endServices ) {
}
