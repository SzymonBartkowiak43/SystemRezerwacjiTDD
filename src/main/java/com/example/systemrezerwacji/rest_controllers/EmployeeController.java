package com.example.systemrezerwacji.rest_controllers;

import com.example.systemrezerwacji.employee_module.EmployeeFacade;
import com.example.systemrezerwacji.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.employee_module.dto.EmployeeToOfferDto;
import com.example.systemrezerwacji.reservation_module.dto.AvailableDatesReservationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeFacade employeeFacade;

    public EmployeeController(EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }

    @GetMapping("/employee-to-offer/{offerId}")
    public ResponseEntity<List<EmployeeToOfferDto>> getEmployeeToOffer(@PathVariable Integer offerId) {
        List<EmployeeToOfferDto> employeesToOffer = employeeFacade.getEmployeesToOffer(offerId.longValue());

        return ResponseEntity.ok(employeesToOffer);
    }

    @RequestMapping(value = "/available-dates", method = RequestMethod.GET)
    public ResponseEntity<List<AvailableTermDto>> getAvailableHours(@RequestBody AvailableDatesReservationDto availableDate) {
        List<AvailableTermDto> availableHours = employeeFacade.getAvailableHours(availableDate);

        if (availableHours.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
          return ResponseEntity.ok(availableHours);
        }

    }

}
