package com.example.systemrezerwacji.domain.salon_module.dto;

import com.example.systemrezerwacji.domain.offer_module.dto.OfferDto;
import jakarta.annotation.Nullable;

import java.util.List;

public record SalonOffersListDto(String message,@Nullable List<OfferDto> offers) {
}
