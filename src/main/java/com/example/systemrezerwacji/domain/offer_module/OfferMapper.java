package com.example.systemrezerwacji.domain.offer_module;

import com.example.systemrezerwacji.domain.offer_module.dto.OfferDto;

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
