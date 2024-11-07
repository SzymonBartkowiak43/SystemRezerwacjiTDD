package com.example.systemrezerwacji.opening_hours_module;


import com.example.systemrezerwacji.day_of_week.DayOfWeek;
import com.example.systemrezerwacji.opening_hours_module.dto.OpeningHoursDto;
import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.salon_module.dto.AddHoursResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class OpeningHoursService {
    private final OpeningHoursRepository openingHoursRepository;

    OpeningHoursService(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
    }


    AddHoursResponseDto addOpeningHours(List<OpeningHoursDto> openingHoursDto, Salon salon) {

        openingHoursRepository.deleteBySalon(salon);
        List<OpeningHours> openingHoursList = openingHoursDto.stream()
                .map(dto -> {
                            OpeningHours openingHours = new OpeningHours();
                            openingHours.setDayOfWeek(DayOfWeek.valueOf(dto.dayOfWeek()));
                            openingHours.setOpeningTime(dto.openingTime());
                            openingHours.setClosingTime(dto.closingTime());
                            openingHours.setSalon(salon);
                            return openingHours;
                        })
                .collect(Collectors.toList());

        Iterable<OpeningHours> openingHours = openingHoursRepository.saveAll(openingHoursList);

        return new AddHoursResponseDto("success", openingHoursList);
    }


}
