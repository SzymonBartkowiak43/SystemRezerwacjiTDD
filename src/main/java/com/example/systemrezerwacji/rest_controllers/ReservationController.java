package com.example.systemrezerwacji.rest_controllers;


import com.example.systemrezerwacji.reservation_module.ReservationFacade;
import com.example.systemrezerwacji.reservation_module.dto.CreateReservationDto;
import com.example.systemrezerwacji.reservation_module.dto.ReservationFacadeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final ReservationFacade reservationFacade;

    public ReservationController(ReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }


    @PostMapping("/reservation")
    public ResponseEntity<ReservationFacadeResponse> createNewReservation(@RequestBody CreateReservationDto reservationDto) {

        ReservationFacadeResponse response =  reservationFacade.createNewReservation(reservationDto);

        if(response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }

    }

}
