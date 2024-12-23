package com.example.systemrezerwacji.domain.opening_hours_module;

import com.example.systemrezerwacji.infrastructure.day_of_week.DayOfWeek;
import com.example.systemrezerwacji.domain.salon_module.Salon;
import com.example.systemrezerwacji.domain.opening_hours_module.dto.OpeningHoursDto;

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
