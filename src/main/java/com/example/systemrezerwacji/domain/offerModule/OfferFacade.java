package com.example.systemrezerwacji.domain.offerModule;

import com.example.systemrezerwacji.domain.offerModule.dto.CreateOfferDto;
import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;
import com.example.systemrezerwacji.domain.offerModule.response.OfferFacadeResponse;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component

public class OfferFacade {
    private final OfferService offerService;
    private final  SalonFacade salonFacade;

    public OfferFacade(OfferService offerService, @Lazy SalonFacade salonFacade) {
        this.offerService = offerService;
        this.salonFacade = salonFacade;
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
        Salon salon = salonFacade.getSalon(Long.valueOf(createOfferDto.salonId()));
        Offer offer = offerService.createOffer(createOfferDto, salon);
        return OfferFacadeResponse.builder()
                .OfferId(offer.getId())
                .message("success")
                .build();
    }
}
