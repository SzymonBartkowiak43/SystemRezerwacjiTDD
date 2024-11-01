package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.SalonDto;
import com.example.systemrezerwacji.salon_module.dto.SalonWithIdDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
class SalonService {
    private final SalonRepository salonRepository;
    private final MaperSalonToSalonWithIdDto mapper;

    SalonService(SalonRepository salonRepository, MaperSalonToSalonWithIdDto mapper) {
        this.salonRepository = salonRepository;
        this.mapper = mapper;
    }

    Long createNewSalon(SalonDto salonDto) {
        Salon salon = new Salon.SalonBuilder()
                .addName(salonDto.salonName())
                .addCity(salonDto.city())
                .addCategory(salonDto.category())
                .addZipCode(salonDto.zipCode())
                .addStreet(salonDto.street())
                .addNumber(salonDto.number())
                .build();
        salonRepository.save(salon);

        return salon.getId();
    }

    List<SalonWithIdDto> getAllSalons() {
        Iterable<Salon> allSalon = salonRepository.findAll();

        return StreamSupport.stream(allSalon.spliterator(), false)
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    Optional<SalonWithIdDto> getSalonById(Long id) {
        Optional<Salon> optionalSalon = salonRepository.findById(id);

        return optionalSalon.map(mapper::map);
    }
}
