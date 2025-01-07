package com.example.systemrezerwacji.infrastructure.restControllers;


import com.example.systemrezerwacji.domain.reservationModule.ReservationFacade;
import com.example.systemrezerwacji.domain.reservationModule.dto.CreateReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.UserReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.response.ReservationFacadeResponse;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/reservation/user")
    public ResponseEntity<List<UserReservationDto>> showReservationToCurrentUser(@RequestParam String email) {
         List<UserReservationDto> userReservationList =  reservationFacade.getUserReservation(email);

         return ResponseEntity.ok(userReservationList);
    }



}
