package com.example.systemrezerwacji.domain.reservation_module.dto;


import java.time.LocalDateTime;

public record CreateReservationDto(Long employeeId, Long offerId,Long salonId, LocalDateTime reservationDateTime, String userEmail) {
}
