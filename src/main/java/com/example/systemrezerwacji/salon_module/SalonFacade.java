package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.code_module.CodeFacade;
import com.example.systemrezerwacji.code_module.ConsumeMessage;
import com.example.systemrezerwacji.employee_module.EmployeeFacade;
import com.example.systemrezerwacji.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.opening_hours_module.OpeningHoursFacade;
import com.example.systemrezerwacji.salon_module.dto.AddHoursResponseDto;
import com.example.systemrezerwacji.salon_module.dto.*;
import com.example.systemrezerwacji.opening_hours_module.dto.OpeningHoursDto;
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
    private final CodeFacade codeFacade;
    private final OpeningHoursFacade openingHoursFacade;
    private final EmployeeFacade employeeFacade;

    public SalonFacade(SalonValidator validator, SalonService salonService, UserFacade userFacade,
                       CodeFacade codeFacade, OpeningHoursFacade openingHoursFacade, EmployeeFacade employeeFacade) {
        this.validator = validator;
        this.salonService = salonService;
        this.userFacade = userFacade;
        this.codeFacade = codeFacade;
        this.openingHoursFacade = openingHoursFacade;
        this.employeeFacade = employeeFacade;
    }


    public SalonFacadeResponseDto createNewSalon(CreatedNewSalonDto salonDto) {

        SalonValidationResult validate = validator.validate(salonDto);
        String message = validate.validationMessage();

        if(!message.equals(SUCCESS_MESSAGE)) {
            return new SalonFacadeResponseDto(message, null);
        }


        Optional<User> user = userFacade.getUserToCreateSalon(Long.valueOf(salonDto.userId()));

        if(user.isEmpty()) {
            return new SalonFacadeResponseDto("User not found", null);
        }

        ConsumeMessage consumeMessage = codeFacade.consumeCode(salonDto.code(), user.get());

        if(consumeMessage.isSuccess()) {
            Optional<User> owner = userFacade.addUserRoleOwner(user.get().getId());
            Long newSalonId = salonService.createNewSalon(salonDto, owner);
            return new SalonFacadeResponseDto(message,newSalonId);
        } else {
            return new SalonFacadeResponseDto(consumeMessage.message(), null);
        }
    }

    public SalonFacadeResponseDto addOpeningHoursToSalon(List<OpeningHoursDto> openingHours) {

        Long salonId = openingHours.get(0).salonId();
        Optional<Salon> salon = salonService.getSalon(salonId);

        AddHoursResponseDto response = openingHoursFacade.addOpeningHours(openingHours, salon.get());

        salon.get().addOpeningHours(response.openingHours());

        return new SalonFacadeResponseDto("success", salonId);
    }

    public CreateEmployeeResponseDto addEmployeeToSalon(EmployeeDto employeeDto) {
        Optional<Salon> salonOptional = salonService.getSalon(employeeDto.salonId());
        if (salonOptional.isEmpty()) {
            return new CreateEmployeeResponseDto ("Salon not found", "", "");
        }
        Salon salon = salonOptional.get();

        CreateEmployeeResponseDto employeeResponse = employeeFacade.addEmployeeToSalon(employeeDto, salon);

        return employeeResponse;
    }



    public List<SalonWithIdDto> getAllSalons() {
        return salonService.getAllSalons();
    }

    public Optional<SalonWithIdDto> getSalonByid(Long id) {
        return salonService.getSalonById(id);
    }

}
