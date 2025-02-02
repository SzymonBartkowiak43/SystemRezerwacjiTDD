package com.example.systemrezerwacji.domain.salonModule.dto;

import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.ReservationDto;

import java.util.List;

public record OwnerSalonWithAllInformation(List<ReservationDto> reservationDto, List<EmployeeDto> employeeDto, List<OfferDto> offerDto, String salonName) {
}
