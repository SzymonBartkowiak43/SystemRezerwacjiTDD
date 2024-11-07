package com.example.systemrezerwacji.offer_module.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public record OfferDto(Long id,
                       String name,
                       String description,
                       BigDecimal price,
                       LocalTime duration) {
}
