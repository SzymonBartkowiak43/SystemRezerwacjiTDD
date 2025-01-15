package com.example.systemrezerwacji.infrastructure.restControllers;

import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import com.example.systemrezerwacji.domain.salonModule.dto.SalonWithIdDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/owner")
@AllArgsConstructor
public class OwnerController {

    private final SalonFacade salonFacade;

    @GetMapping("/salons/{ownerId}")
    public ResponseEntity<List<SalonWithIdDto>> getAllSalonsToOwnerId(@PathVariable Integer ownerId) {
        List<SalonWithIdDto> allSalons = salonFacade.getAllSalonsToOwner(ownerId);

        return ResponseEntity.ok(allSalons);
    }
//
//    @GetMapping("/salons/{salonId}/all-reservation")



}
