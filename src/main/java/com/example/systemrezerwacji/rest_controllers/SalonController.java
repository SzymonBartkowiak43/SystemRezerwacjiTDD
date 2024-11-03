package com.example.systemrezerwacji.rest_controllers;


import com.example.systemrezerwacji.salon_module.SalonFacade;
import com.example.systemrezerwacji.salon_module.dto.CreatedNewSalonDto;
import com.example.systemrezerwacji.salon_module.dto.SalonFacadeDto;
import com.example.systemrezerwacji.salon_module.dto.SalonWithIdDto;
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
    public ResponseEntity<SalonFacadeDto> createSalon(@RequestBody CreatedNewSalonDto salon) {
        SalonFacadeDto newSalon = salonFacade.createNewSalon(salon);

        if(newSalon.salonId() == null) {
            return ResponseEntity.badRequest().body(newSalon);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSalon.salonId())
                .toUri();

        return ResponseEntity.created(location).body(newSalon);
    }

    @GetMapping("/salons")
    public ResponseEntity<List<SalonWithIdDto>> getAllSalons() {
        List<SalonWithIdDto> allSalons = salonFacade.getAllSalons();

        return ResponseEntity.ok(allSalons);
    }

    @GetMapping("/salon/{id}")
    public ResponseEntity<SalonWithIdDto> getSalon(@PathVariable Integer id) {
        return salonFacade.getSalonByid(id.longValue())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

 }
