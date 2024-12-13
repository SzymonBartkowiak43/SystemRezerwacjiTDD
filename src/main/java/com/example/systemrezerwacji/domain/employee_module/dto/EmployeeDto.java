package com.example.systemrezerwacji.domain.employee_module.dto;


import java.util.List;


public record EmployeeDto(Long salonId, String name, String email, List<EmployeeAvailabilityDto> availability) {}