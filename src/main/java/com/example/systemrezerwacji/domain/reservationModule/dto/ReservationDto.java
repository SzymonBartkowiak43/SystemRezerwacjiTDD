package com.example.systemrezerwacji.domain.reservationModule.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationDto(Long reservationId,
                             String employeeName,
                             String offerName,
                             BigDecimal price,
                             LocalDateTime reservationDateTimeStart,
                             LocalDateTime reservationDateTimeEnd) {
}

