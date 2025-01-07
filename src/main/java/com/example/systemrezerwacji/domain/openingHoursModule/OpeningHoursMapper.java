package com.example.systemrezerwacji.domain.openingHoursModule;

import com.example.systemrezerwacji.infrastructure.dayOfWeek.DayOfWeek;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.openingHoursModule.dto.OpeningHoursDto;

class OpeningHoursMapper {
    static OpeningHours toEntity(OpeningHoursDto dto, Salon salon) {
        OpeningHours openingHours = new OpeningHours();

        openingHours.setDayOfWeek(DayOfWeek.valueOf(dto.dayOfWeek().toUpperCase()));
        openingHours.setOpeningTime(dto.openingTime());
        openingHours.setClosingTime(dto.closingTime());
        openingHours.setSalon(salon);

        return openingHours;
    }
}
