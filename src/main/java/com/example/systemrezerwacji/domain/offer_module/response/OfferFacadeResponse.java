package com.example.systemrezerwacji.domain.offer_module.response;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record OfferFacadeResponse(String message, @Nullable Long OfferId) {

}
