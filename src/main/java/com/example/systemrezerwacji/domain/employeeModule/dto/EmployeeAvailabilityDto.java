package com.example.systemrezerwacji.domain.employeeModule.dto;


import java.time.LocalTime;

public record EmployeeAvailabilityDto(String dayOfWeek, LocalTime startTime, LocalTime endTime) {}