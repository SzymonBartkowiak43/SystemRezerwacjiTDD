package com.example.systemrezerwacji.offer_module;

import com.example.systemrezerwacji.offer_module.dto.OfferDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> findOffers(List<Long> offersId) {
        return null;
    }

    public List<OfferDto> getAllOffers(Long salonId) {
        List<Offer> allOffers= offerRepository.findAllBySalonId(salonId);

        return allOffers.stream()
                .map(OfferMapper::toDto)
                .toList();
    }
}
