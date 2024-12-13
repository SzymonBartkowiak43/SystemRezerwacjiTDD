package com.example.systemrezerwacji.domain.offer_module.dto;

import java.math.BigDecimal;
import java.time.LocalTime;

public record OfferDto(Long id,
                       String name,
                       String description,
                       BigDecimal price,
                       LocalTime duration) {
}