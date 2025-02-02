package com.example.systemrezerwacji.infrastructure.restControllers;

import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import com.example.systemrezerwacji.domain.salonModule.dto.OwnerSalonWithAllInformation;
import com.example.systemrezerwacji.domain.salonModule.dto.SalonWithIdDto;
import com.example.systemrezerwacji.domain.userModule.dto.OwnerMailDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
@AllArgsConstructor
public class OwnerController {

    private final SalonFacade salonFacade;

    @GetMapping("/salons")
    public ResponseEntity<List<SalonWithIdDto>> getAllSalonsToOwner(@RequestBody OwnerMailDto ownerMailDto) {
        List<SalonWithIdDto> allSalons = salonFacade.getAllSalonsToOwner(ownerMailDto.email());

        return ResponseEntity.ok(allSalons);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<OwnerSalonWithAllInformation> getSalonById(@PathVariable Long salonId, @RequestBody OwnerMailDto ownerMailDto) {
        OwnerSalonWithAllInformation salon = salonFacade.getSalonByIdToOwner(salonId, ownerMailDto.email());

        return ResponseEntity.ok(salon);
    }




}
