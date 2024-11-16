package com.example.systemrezerwacji.offer_module;

import com.example.systemrezerwacji.offer_module.dto.OfferDto;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
class OfferService {
    private final OfferRepository offerRepository;

    OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    List<Offer> findOffers(List<Long> offersId) {
        return null;
    }

    List<OfferDto> getAllOffers(Long salonId) {
        List<Offer> allOffers= offerRepository.findAllBySalonId(salonId);

        return allOffers.stream()
                .map(OfferMapper::toDto)
                .toList();
    }

    LocalTime getDurationByOfferId(Long offerId) {
        Offer offerById = offerRepository.findOfferById(offerId);

        return offerById.getDuration();
    }

    Offer getOffer(Long offerId) {
        return offerRepository.findOfferById(offerId);
    }
}
