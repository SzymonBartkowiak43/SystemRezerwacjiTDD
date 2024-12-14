package com.example.systemrezerwacji.infrastructure.rest_controllers;


import com.example.systemrezerwacji.domain.reservation_module.ReservationFacade;
import com.example.systemrezerwacji.domain.reservation_module.dto.CreateReservationDto;
import com.example.systemrezerwacji.domain.reservation_module.dto.UserReservationDto;
import com.example.systemrezerwacji.domain.reservation_module.response.ReservationFacadeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/reservation/user/{userId}")
    public ResponseEntity<List<UserReservationDto>> showReservationToCurrentUser(@PathVariable Long userId) {
         List<UserReservationDto> userReservationList =  reservationFacade.getUserReservation(userId);

         return ResponseEntity.ok(userReservationList);
    }



}
