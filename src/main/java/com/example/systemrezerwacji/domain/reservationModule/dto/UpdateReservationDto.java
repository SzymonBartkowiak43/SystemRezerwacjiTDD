package com.example.systemrezerwacji.domain.reservationModule.dto;

import java.time.LocalDateTime;

public record UpdateReservationDto(Long reservationId, LocalDateTime newReservationDate) {
}
