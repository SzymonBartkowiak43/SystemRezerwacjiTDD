package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.SalonFacadeDto;

public class SalonFacade {
    SalonValidator validator = new SalonValidator();


    SalonFacadeDto inputStrings(String name,
                                String category,
                                String city,
                                String zipCode,
                                String street,
                                String number) {
        SalonValidationResult validate = validator.validate(name, category, city, zipCode, street, number);
        String message = validate.validationMessage();
        return new SalonFacadeDto(message);
    }
}
