package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.CreatedNewSalonDto;
import com.example.systemrezerwacji.salon_module.dto.SalonFacadeDto;
import com.example.systemrezerwacji.salon_module.dto.SalonWithIdDto;
import com.example.systemrezerwacji.user_module.User;
import com.example.systemrezerwacji.user_module.UserFacade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.example.systemrezerwacji.salon_module.SalonValidationResult.SUCCESS_MESSAGE;

@Component
public class SalonFacade {
    private final SalonValidator validator;
    private final SalonService salonService;
    private final UserFacade userFacade;

    public SalonFacade(SalonValidator validator, SalonService salonService, UserFacade userFacade) {
        this.validator = validator;
        this.salonService = salonService;
        this.userFacade = userFacade;
    }


    public SalonFacadeDto createNewSalon(CreatedNewSalonDto salonDto) {
        SalonValidationResult validate = validator.validate(salonDto);
        String message = validate.validationMessage();

        if(!message.equals(SUCCESS_MESSAGE)) {
            return new SalonFacadeDto(message, null);
        }
        Optional<User> user = userFacade.getUserToCreateSalon(Long.valueOf(salonDto.userId()));

        Long newSalonId = salonService.createNewSalon(salonDto, user);

        return new SalonFacadeDto(message,newSalonId);
    }

    public List<SalonWithIdDto> getAllSalons() {
        return salonService.getAllSalons();
    }

    public Optional<SalonWithIdDto> getSalonByid(Long id) {
        return salonService.getSalonById(id);
    }

}
