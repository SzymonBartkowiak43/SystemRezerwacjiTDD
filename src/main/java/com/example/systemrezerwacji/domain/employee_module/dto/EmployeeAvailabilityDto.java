package com.example.systemrezerwacji.domain.employee_module.dto;


import java.time.LocalTime;

public record EmployeeAvailabilityDto(String dayOfWeek, LocalTime startTime, LocalTime endTime) {}