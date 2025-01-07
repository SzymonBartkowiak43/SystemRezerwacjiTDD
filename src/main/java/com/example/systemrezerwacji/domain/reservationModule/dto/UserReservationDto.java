package com.example.systemrezerwacji.domain.reservationModule.dto;

import java.time.LocalDateTime;

public record UserReservationDto(
        Long salonId,
        Long employeeId,
        Long userId,
        Long offerId,
        LocalDateTime reservationDateTime) {
}

