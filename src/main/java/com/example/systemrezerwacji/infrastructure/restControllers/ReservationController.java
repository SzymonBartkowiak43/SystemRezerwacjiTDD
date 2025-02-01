package com.example.systemrezerwacji.infrastructure.restControllers;


import com.example.systemrezerwacji.domain.employeeModule.dto.AvailableTermWithDateDto;
import com.example.systemrezerwacji.domain.reservationModule.ReservationFacade;
import com.example.systemrezerwacji.domain.reservationModule.dto.*;
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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/reservations")
    public ResponseEntity<List<UserReservationDataDto>> showReservationToCurrentUser(@RequestParam String email) {
        List<UserReservationDataDto> userReservationList =  reservationFacade.getUserReservation(email);

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

    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/reservation")
    public ResponseEntity<UserReservationDto> changeDateOfReservation(@RequestBody UpdateReservationDto updateReservationDto) {
        log.info("Updating reservation date: " + updateReservationDto);
        UserReservationDto response = reservationFacade.updateReservationDate(updateReservationDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reservation/{reservationId}/nearest")
    public ResponseEntity<List<AvailableTermWithDateDto>> getNearest5AvailableTerm(@PathVariable Long reservationId) {
        List<AvailableTermWithDateDto> userReservationList = reservationFacade.getNearest5AvailableHours(reservationId);
        return ResponseEntity.ok(userReservationList);
    }



}
