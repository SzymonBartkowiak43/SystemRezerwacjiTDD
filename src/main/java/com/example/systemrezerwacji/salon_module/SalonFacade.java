package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.SalonDto;
import com.example.systemrezerwacji.salon_module.dto.SalonFacadeDto;
import com.example.systemrezerwacji.salon_module.dto.SalonWithIdDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.example.systemrezerwacji.salon_module.SalonValidationResult.SUCCESS_MESSAGE;

@Component
public class SalonFacade {
    private final SalonValidator validator;
    private final SalonService salonService;

    public SalonFacade(SalonValidator validator, SalonService salonService) {
        this.validator = validator;
        this.salonService = salonService;
    }


    public SalonFacadeDto createNewSalon(SalonDto salonDto) {
        SalonValidationResult validate = validator.validate(salonDto);
        String message = validate.validationMessage();

        if(!message.equals(SUCCESS_MESSAGE)) {
            return new SalonFacadeDto(message, null);
        }
        Long newSalonId = salonService.createNewSalon(salonDto);

        return new SalonFacadeDto(message,newSalonId);
    }

    public List<SalonWithIdDto> getAllSalons() {
        return salonService.getAllSalons();
    }

    public Optional<SalonWithIdDto> getSalonByid(Long id) {
        return salonService.getSalonById(id);
    }

}
