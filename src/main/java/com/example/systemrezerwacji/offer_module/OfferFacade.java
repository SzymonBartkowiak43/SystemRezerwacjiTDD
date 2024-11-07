package com.example.systemrezerwacji.offer_module;

import com.example.systemrezerwacji.offer_module.dto.OfferDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfferFacade {
    private final OfferService offerService;

    public OfferFacade(OfferService serviceService) {
        this.offerService = serviceService;
    }

    List<Offer> findAllOffersById(List<Long> servicesId) {
        offerService.findOffers(servicesId);

        return null;
    }

    public List<OfferDto> getAllOffers(Long salonId) {
        return offerService.getAllOffers(salonId);
    }

}
