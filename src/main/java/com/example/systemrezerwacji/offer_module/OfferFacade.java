package com.example.systemrezerwacji.offer_module;

import com.example.systemrezerwacji.offer_module.dto.CreateOfferDto;
import com.example.systemrezerwacji.offer_module.dto.OfferDto;
import com.example.systemrezerwacji.offer_module.response.OfferFacadeResponse;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class OfferFacade {
    private final OfferService offerService;

    public OfferFacade(OfferService serviceService) {
        this.offerService = serviceService;
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

    public OfferFacadeResponse createOffer(CreateOfferDto createOfferDto) {
        return null;
    }
}
