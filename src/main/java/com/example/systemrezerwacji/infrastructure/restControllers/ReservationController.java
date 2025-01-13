package com.example.systemrezerwacji.infrastructure.restControllers;


import com.example.systemrezerwacji.domain.reservationModule.ReservationFacade;
import com.example.systemrezerwacji.domain.reservationModule.dto.CreateReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.DeleteReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.UpdateReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.UserReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.response.ReservationFacadeResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Log4j2
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

    @GetMapping("/reservation")
    public ResponseEntity<List<UserReservationDto>> showReservationToCurrentUser(@RequestParam String email) {
        List<UserReservationDto> userReservationList =  reservationFacade.getUserReservation(email);

        return ResponseEntity.ok(userReservationList);
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<ReservationFacadeResponse> deleteReservation(@RequestBody DeleteReservationDto deleteReservationDto) {
        ReservationFacadeResponse response = reservationFacade.deleteReservation(deleteReservationDto);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PatchMapping("/reservation")
    public ResponseEntity<UserReservationDto> changeDateOfReservation(@RequestBody UpdateReservationDto updateReservationDto) {
        log.info("Updating reservation date: " + updateReservationDto);
        UserReservationDto response = reservationFacade.updateReservationDate(updateReservationDto);

        return ResponseEntity.ok(response);
    }



}
