package com.example.systemrezerwacji.rest_controllers;

import com.example.systemrezerwacji.employee_module.EmployeeFacade;
import com.example.systemrezerwacji.employee_module.dto.EmployeeToOfferDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeFacade employeeFacade;

    public EmployeeController(EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }

    @GetMapping("/employee-to-offer/{id}")
    public ResponseEntity<List<EmployeeToOfferDto>> getEmployeeToOffer(@PathVariable Integer id) {
        List<EmployeeToOfferDto> employeesToOffer = employeeFacade.getEmployeesToOffer(id.longValue());

        return ResponseEntity.ok(employeesToOffer);
    }
}
