package com.example.systemrezerwacji.salon_module.dto;

import com.example.systemrezerwacji.offer_module.dto.OfferDto;
import jakarta.annotation.Nullable;

import java.util.List;

public record SalonOffersListDto(String message,@Nullable List<OfferDto> offers) {
}
