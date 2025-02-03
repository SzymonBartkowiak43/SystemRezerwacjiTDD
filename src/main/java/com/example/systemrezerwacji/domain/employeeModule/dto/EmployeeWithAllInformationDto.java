package com.example.systemrezerwacji.domain.employeeModule.dto;

import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeAvailabilityDto;
import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;

import java.util.List;

public record EmployeeWithAllInformationDto (Long employeeId, Long salonId, String name, String email, List<EmployeeAvailabilityDto> availability, List<OfferDto> offerList) {}
