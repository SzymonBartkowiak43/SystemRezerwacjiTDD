package com.example.systemrezerwacji.reservation_module.dto;

import java.time.LocalDate;

public record AvailableDatesReservationDto(LocalDate date, Long employeeId, Long offerId) {
}
