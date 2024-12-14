package com.example.systemrezerwacji.infrastructure.rest_controllers;

import com.example.systemrezerwacji.domain.offer_module.OfferFacade;
import com.example.systemrezerwacji.domain.offer_module.dto.CreateOfferDto;
import com.example.systemrezerwacji.domain.offer_module.response.OfferFacadeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class OfferController {

    private final OfferFacade offerFacade;

    public OfferController(OfferFacade offerFacade) {
        this.offerFacade = offerFacade;
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


}
