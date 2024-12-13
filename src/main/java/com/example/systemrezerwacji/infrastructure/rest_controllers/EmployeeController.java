package com.example.systemrezerwacji.infrastructure.rest_controllers;

import com.example.systemrezerwacji.domain.employee_module.EmployeeFacade;
import com.example.systemrezerwacji.domain.employee_module.dto.AddOfferRequestDto;
import com.example.systemrezerwacji.domain.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.domain.employee_module.dto.EmployeeFacadeResponseDto;
import com.example.systemrezerwacji.domain.employee_module.dto.EmployeeToOfferDto;
import com.example.systemrezerwacji.domain.reservation_module.dto.AvailableDatesReservationDto;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/available-dates")
    public ResponseEntity<List<AvailableTermDto>> getAvailableHours(@RequestBody AvailableDatesReservationDto availableDate) {
        List<AvailableTermDto> availableHours = employeeFacade.getAvailableHours(availableDate);

        if (availableHours.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(availableHours);
    }

    @PatchMapping("employees/{employeeId}/offers")
    public ResponseEntity<EmployeeFacadeResponseDto> addOfferToEmployee(@PathVariable Long employeeId, @RequestBody AddOfferRequestDto offer) {
        EmployeeFacadeResponseDto response = employeeFacade.addOfferToEmployee(employeeId, offer.offerId());

        if (response.message().equals("success")) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }



}
