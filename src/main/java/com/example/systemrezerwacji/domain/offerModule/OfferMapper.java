package com.example.systemrezerwacji.domain.offerModule;

import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;

class OfferMapper {
    static OfferDto toDto(Offer offer) {
        return new OfferDto(
                offer.getId(),
                offer.getName(),
                offer.getDescription(),
                offer.getPrice(),
                offer.getDuration()
        );
    }
}
