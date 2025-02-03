package com.example.systemrezerwacji.domain.salonModule.dto;

import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeWithAllInformationDto;
import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.ReservationDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record OwnerSalonWithAllInformation(Map<LocalDate, List<ReservationDto>> reservationDto, List<EmployeeWithAllInformationDto> employeeDto, List<OfferDto> offerDto, String salonName) {
}
