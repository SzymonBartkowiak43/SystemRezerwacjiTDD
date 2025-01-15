package com.example.systemrezerwacji.domain.salonModule;

import com.example.systemrezerwacji.domain.salonModule.dto.CreateNewSalonDto;
import com.example.systemrezerwacji.domain.salonModule.dto.ImageDto;
import com.example.systemrezerwacji.domain.salonModule.dto.SalonWithIdDto;
import com.example.systemrezerwacji.domain.salonModule.exception.SalonNotFoundException;
import com.example.systemrezerwacji.domain.userModule.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
class SalonService {
    private final SalonRepository salonRepository;
    private final MaperSalonToSalonWithIdDto mapper;
    private final ImageRepository imageRepository;

    SalonService(SalonRepository salonRepository, MaperSalonToSalonWithIdDto mapper, ImageRepository imageRepository) {
        this.salonRepository = salonRepository;
        this.mapper = mapper;
        this.imageRepository = imageRepository;
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



    public Salon addImageToSalon(Long salonId, Image image) {
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> new RuntimeException("Salon not found"));
        image.setSalon(salon);
        salon.getImages().add(image);
        return salonRepository.save(salon);
    }

    public List<ImageDto> findImagesBySalonId(Long salonId) {
        List<Image> images = imageRepository.findBySalonId(salonId);
        return images.stream()
                .map(image -> new ImageDto(image.getId(), image.getName(), image.getImageUrl(), image.getImageId(), image.getSalon().getId()))
                .collect(Collectors.toList());
    }


    public List<SalonWithIdDto> getAllSalons(User user) {
        List<Salon> salonsByUser = salonRepository.getSalonsByUser(user);

        return salonsByUser.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}
