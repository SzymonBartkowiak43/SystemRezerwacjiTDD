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
@CrossOrigin(origins = "http://localhost:3000")
public class OwnerController {

    private final SalonFacade salonFacade;

    @GetMapping("/salons")
    public ResponseEntity<List<SalonWithIdDto>> getAllSalonsToOwner(@RequestParam String email) {
        List<SalonWithIdDto> allSalons = salonFacade.getAllSalonsToOwner(email);
        return ResponseEntity.ok(allSalons);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<OwnerSalonWithAllInformation> getSalonById(@PathVariable Long salonId, @RequestParam String email) {
        OwnerSalonWithAllInformation salon = salonFacade.getSalonByIdToOwner(salonId, email);

        return ResponseEntity.ok(salon);
    }




}
