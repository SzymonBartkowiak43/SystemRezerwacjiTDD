package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.SalonFacadeDto;

import static com.example.systemrezerwacji.salon_module.SalonValidationResult.SUCCESS_MESSAGE;

public class SalonFacade {
    SalonValidator validator = new SalonValidator();
    SalonService salonService = new SalonService();


    public SalonFacadeDto createNewSalon(String name,
                                         String category,
                                         String city,
                                         String zipCode,
                                         String street,
                                         String number) {
        SalonValidationResult validate = validator.validate(name, category, city, zipCode, street, number);
        String message = validate.validationMessage();

        if(!message.equals(SUCCESS_MESSAGE)) {
            return new SalonFacadeDto(message);
        }
        salonService.createNewSalon(name, category, city, zipCode, street, number);

        return new SalonFacadeDto(message);

    }
}
