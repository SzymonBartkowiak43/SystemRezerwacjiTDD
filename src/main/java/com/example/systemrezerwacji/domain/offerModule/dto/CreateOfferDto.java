package com.example.systemrezerwacji.domain.offerModule.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public record CreateOfferDto(
                       String name,
                       String description,
                       BigDecimal price,
                       LocalTime duration,
                       Integer salonId) {
}
