package com.example.systemrezerwacji.domain.reservation_module.dto;

import java.time.LocalDateTime;

public record ReservationToTomorrow(Long salonId, Long userId, Long offerId, LocalDateTime reservationDataTime) {
}
