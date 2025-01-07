package com.example.systemrezerwacji.domain.salonModule.dto;

import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;
import jakarta.annotation.Nullable;

import java.util.List;

public record SalonOffersListDto(String message,@Nullable List<OfferDto> offers) {
}
