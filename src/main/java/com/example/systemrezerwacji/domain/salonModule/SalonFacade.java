package com.example.systemrezerwacji.domain.salonModule;


import com.example.systemrezerwacji.domain.employeeModule.EmployeeFacade;
import com.example.systemrezerwacji.domain.employeeModule.response.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.offerModule.OfferFacade;
import com.example.systemrezerwacji.domain.offerModule.dto.OfferDto;
import com.example.systemrezerwacji.domain.openingHoursModule.dto.OpeningHoursDto;
import com.example.systemrezerwacji.domain.reservationModule.ReservationFacade;
import com.example.systemrezerwacji.domain.salonModule.dto.*;
import com.example.systemrezerwacji.domain.salonModule.exception.SalonCreationException;
import com.example.systemrezerwacji.domain.userModule.User;
import com.example.systemrezerwacji.domain.userModule.UserFacade;
import com.example.systemrezerwacji.domain.openingHoursModule.OpeningHoursFacade;
import com.example.systemrezerwacji.domain.salonModule.dto.AddHoursResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SalonFacade {

    private final SalonService salonService;
    private final UserFacade userFacade;
    private final OpeningHoursFacade openingHoursFacade;
    private final EmployeeFacade employeeFacade;
    private final OfferFacade offerFacade;
    private final SalonCreator salonCreator;
    private final ReservationFacade reservationFacade;


    public SalonFacadeResponseDto createNewSalon(CreateNewSalonDto salonDto) {
        try {
            Long salonId = salonCreator.create(salonDto);
            return new SalonFacadeResponseDto(SalonValidationResult.SUCCESS_MESSAGE, salonId);
        } catch (SalonCreationException e) {
            return new SalonFacadeResponseDto(e.getMessage(), null);
        }
    }

    public SalonFacadeResponseDto addOpeningHoursToSalon(List<OpeningHoursDto> openingHours) {
        Long salonId = openingHours.get(0).salonId();
        Salon salon = salonService.getSalon(salonId);

        AddHoursResponseDto response = openingHoursFacade.addOpeningHours(openingHours, salon);
        salon.addOpeningHours(response.openingHours());

        return new SalonFacadeResponseDto("success", salonId);
    }

    public CreateEmployeeResponseDto addEmployeeToSalon(EmployeeDto employeeDto) {
        Salon salon= salonService.getSalon(employeeDto.salonId());

        return employeeFacade.createEmployeeAndAddToSalon(employeeDto, salon);
    }

    public SalonOffersListDto getAllOffersToSalon(Long salonId) {
        List<OfferDto> allOffers = offerFacade.getAllOffers(salonId);

        return new SalonOffersListDto("success", allOffers);
    }

    @Cacheable(value = "salons", unless = "#result.isEmpty()")
    public List<SalonWithIdDto> getAllSalons() {
        return salonService.getAllSalons();
    }

    public Optional<SalonWithIdDto> getSalonById(Long id) {
        return salonService.getSalonById(id);
    }


    public Salon getSalon(Long id) {
        return salonService.getSalon(id);
    }

//*******************************IMAGE*******************************************
    public void addImageToSalon(Long salonId, Image image) {
        Salon salon = salonService.addImageToSalon(salonId, image);
    }

    public List<ImageDto> findImagesBySalonId(Long salonId) {
        return salonService.findImagesBySalonId(salonId);

    }
//*******************************OWNER*******************************************
    public List<SalonWithIdDto> getAllSalonsToOwner(String email) {
        User user = userFacade.getUserByEmail(email);
        List<SalonWithIdDto> allSalons =  salonService.getAllSalons(user);
        return allSalons;
    }

    public OwnerSalonWithAllInformation getSalonByIdToOwner(Long salonId, String email) {
        User user = userFacade.getUserByEmail(email);
        SalonWithIdDto salon = salonService.getSalonByIdAndCheckOwner(salonId, user);
//        reservationFacade.get
//
//        return salon;
        return null;
    }
}






























