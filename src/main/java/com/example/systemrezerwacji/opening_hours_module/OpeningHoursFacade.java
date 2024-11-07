package com.example.systemrezerwacji.opening_hours_module;

import com.example.systemrezerwacji.opening_hours_module.dto.OpeningHoursDto;
import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.salon_module.dto.AddHoursResponseDto;

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
