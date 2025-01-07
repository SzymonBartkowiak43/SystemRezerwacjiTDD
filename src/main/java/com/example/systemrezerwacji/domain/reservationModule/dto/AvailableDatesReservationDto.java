package com.example.systemrezerwacji.domain.reservationModule.dto;

import java.time.LocalDate;

public record AvailableDatesReservationDto(LocalDate date, Long employeeId, Long offerId) {
}
