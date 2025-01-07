package com.example.systemrezerwacji.domain.employeeModule.dto;


import lombok.Builder;

import java.util.List;


@Builder
public record EmployeeDto(Long salonId, String name, String email, List<EmployeeAvailabilityDto> availability) {}