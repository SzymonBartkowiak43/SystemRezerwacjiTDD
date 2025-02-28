package com.example.systemrezerwacji.domain.offerModule;

import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OfferConfiguration {

    @Mock
    SalonFacade salonFacade;

    public OfferConfiguration() {
        MockitoAnnotations.openMocks(this);
    }
    public OfferFacade createForTest(OfferRepository offerRepository) {
        OfferService offerService = new OfferService(offerRepository);
        return new OfferFacade(offerService, salonFacade);
    }
}
