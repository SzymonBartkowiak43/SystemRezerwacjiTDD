package com.example.systemrezerwacji.offer_module;

import com.example.systemrezerwacji.offer_module.dto.OfferDto;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class OfferFacade {
    private final OfferService offerService;

    public OfferFacade(OfferService serviceService) {
        this.offerService = serviceService;
    }

    List<Offer> findAllOffersById(List<Long> OffersId) {
        offerService.findOffers(OffersId);

        return null;
    }

    public List<OfferDto> getAllOffers(Long salonId) {
        return offerService.getAllOffers(salonId);
    }

    public LocalTime getDurationToOffer(Long offerId) {
        return offerService.getDurationByOfferId(offerId);
    }

    public Offer getOffer(Long offerId) {
        return offerService.getOffer(offerId);
    }
}
