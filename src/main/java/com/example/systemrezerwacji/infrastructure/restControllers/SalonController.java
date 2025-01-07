package com.example.systemrezerwacji.infrastructure.restControllers;


import com.example.systemrezerwacji.domain.employeeModule.response.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;
import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import com.example.systemrezerwacji.domain.salonModule.dto.*;
import com.example.systemrezerwacji.domain.openingHoursModule.dto.OpeningHoursDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SalonController {
    private final SalonFacade salonFacade;

    public SalonController(SalonFacade salonFacade) {
        this.salonFacade = salonFacade;
    }


    @PostMapping("/salon")
    public ResponseEntity<SalonFacadeResponseDto> createSalon(@RequestBody CreateNewSalonDto salon) {
        SalonFacadeResponseDto newSalon = salonFacade.createNewSalon(salon);

        if(newSalon.salonId() == null) {
            return ResponseEntity.badRequest().body(newSalon);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSalon.salonId())
                .toUri();

        return ResponseEntity.created(location).body(newSalon);
    }

    @PatchMapping("/salon/add-opening-hours")
    public ResponseEntity<SalonFacadeResponseDto> addOpeningHours(@RequestBody List<OpeningHoursDto> openingHoursDto) {
        SalonFacadeResponseDto response = salonFacade.addOpeningHoursToSalon(openingHoursDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/salons")
    public ResponseEntity<List<SalonWithIdDto>> getAllSalons() {
        List<SalonWithIdDto> allSalons = salonFacade.getAllSalons();

        return ResponseEntity.ok(allSalons);
    }

    @GetMapping("/salons/{id}")
    public ResponseEntity<SalonWithIdDto> getSalon(@PathVariable Integer id) {
        return salonFacade.getSalonById(id.longValue())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/salon/{id}/employee")
    public ResponseEntity<CreateEmployeeResponseDto> addEmployeeToSalon(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployeeDto = new EmployeeDto(id, employeeDto.name(), employeeDto.email(), employeeDto.availability());

        CreateEmployeeResponseDto response = salonFacade.addEmployeeToSalon(updatedEmployeeDto);

        if(response.message().equals("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<List<OfferDto>> getOffers(@PathVariable Integer id) {
        SalonOffersListDto allOffers = salonFacade.getAllOffersSalon(id.longValue());

        return ResponseEntity.ok(allOffers.offers());
    }

 }
