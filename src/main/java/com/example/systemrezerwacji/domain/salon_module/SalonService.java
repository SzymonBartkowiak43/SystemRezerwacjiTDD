package com.example.systemrezerwacji.domain.salon_module;

import com.example.systemrezerwacji.domain.salon_module.dto.CreateNewSalonDto;
import com.example.systemrezerwacji.domain.salon_module.dto.SalonWithIdDto;
import com.example.systemrezerwacji.domain.salon_module.exception.SalonNotFoundException;
import com.example.systemrezerwacji.domain.user_module.User;
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

    Long createNewSalon(CreateNewSalonDto salonDto, Optional<User> user) {
        Salon salon = new Salon.SalonBuilder()
                .addName(salonDto.salonName())
                .addCity(salonDto.city())
                .addCategory(salonDto.category())
                .addZipCode(salonDto.zipCode())
                .addStreet(salonDto.street())
                .addNumber(salonDto.number())
                .addUser(user)
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

    Salon getSalon(Long id) {
        return salonRepository.findById(id)
                .orElseThrow(() -> new SalonNotFoundException("Salon with id: " + id + " not found"));
    }
}
