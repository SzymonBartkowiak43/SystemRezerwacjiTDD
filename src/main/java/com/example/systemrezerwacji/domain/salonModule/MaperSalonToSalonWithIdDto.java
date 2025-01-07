package com.example.systemrezerwacji.domain.salonModule;

import com.example.systemrezerwacji.domain.salonModule.dto.SalonWithIdDto;
import org.springframework.stereotype.Component;

@Component
class MaperSalonToSalonWithIdDto {

    SalonWithIdDto map(Salon salon) {
        if (salon == null) {
            return null;
        }

        return new SalonWithIdDto(
                String.valueOf(salon.getId()),
                salon.getSalonName(),
                salon.getCategory(),
                salon.getCity(),
                salon.getZipCode(),
                salon.getStreet(),
                salon.getNumber(),
                salon.getUserid().toString()
        );
    }
}