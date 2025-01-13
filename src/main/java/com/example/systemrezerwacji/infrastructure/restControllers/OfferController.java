package com.example.systemrezerwacji.infrastructure.restControllers;

import com.example.systemrezerwacji.domain.offerModule.OfferFacade;
import com.example.systemrezerwacji.domain.offerModule.dto.CreateOfferDto;
import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;
import com.example.systemrezerwacji.domain.offerModule.response.OfferFacadeResponse;
import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import com.example.systemrezerwacji.domain.salonModule.dto.SalonOffersListDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class OfferController {

    private final OfferFacade offerFacade;
    private final SalonFacade salonFacade;

    public OfferController(OfferFacade offerFacade, SalonFacade salonFacade) {
        this.offerFacade = offerFacade;
        this.salonFacade = salonFacade;
    }

    @PostMapping("/offer")
    public ResponseEntity<OfferFacadeResponse> createNewOffer(@RequestBody CreateOfferDto createOfferDto) {

        OfferFacadeResponse newOffer = offerFacade.createOffer(createOfferDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newOffer.OfferId())
                .toUri();

        return ResponseEntity.created(location).body(newOffer);
    }

    @GetMapping("/offers/{salonId}")
    public ResponseEntity<List<OfferDto>> getOffers(@PathVariable Integer salonId) {
        SalonOffersListDto allOffers = salonFacade.getAllOffersSalon(salonId.longValue());

        return ResponseEntity.ok(allOffers.offers());
    }


}
