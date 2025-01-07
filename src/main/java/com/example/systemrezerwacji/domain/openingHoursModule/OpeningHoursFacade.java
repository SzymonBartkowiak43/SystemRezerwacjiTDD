package com.example.systemrezerwacji.domain.openingHoursModule;

import com.example.systemrezerwacji.domain.openingHoursModule.dto.OpeningHoursDto;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.salonModule.dto.AddHoursResponseDto;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OpeningHoursFacade {
    private final OpeningHoursService openingHoursService;


    public OpeningHoursFacade(OpeningHoursService openingHoursService) {
        this.openingHoursService = openingHoursService;
    }

    @Transactional
    public AddHoursResponseDto addOpeningHours(List<OpeningHoursDto> openingHours, Salon salon) {
        AddHoursResponseDto result = openingHoursService.addOpeningHours(openingHours, salon);

        if(result.message().equals("success")) {
            return result;
        }

        return null;
    }
}
