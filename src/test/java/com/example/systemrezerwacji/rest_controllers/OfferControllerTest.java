package com.example.systemrezerwacji.rest_controllers;

import com.example.systemrezerwacji.offer_module.OfferFacade;
import com.example.systemrezerwacji.offer_module.dto.CreateOfferDto;
import com.example.systemrezerwacji.offer_module.response.OfferFacadeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class OfferControllerTest {
//    @Mock
//    private OfferFacade offerFacade;
//
//    @InjectMocks
//    private OfferController offerController;
//
    @Test
    void should_create_new_offer() {
//        //given
//        String name = "Thai massage";
//        String description = "awesome massage";
//        BigDecimal price = BigDecimal.valueOf(220.00);
//        LocalTime duration = LocalTime.of(1,0);
//        Integer salonId = 1;
//
//        CreateOfferDto createOfferDto = new CreateOfferDto(name, description, price, duration, salonId);
//
//        //when
//        ResponseEntity<OfferFacadeResponse> newOffer = offerController.createNewOffer(createOfferDto);
//
//        //then
//        assertThat(newOffer.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
    }
}