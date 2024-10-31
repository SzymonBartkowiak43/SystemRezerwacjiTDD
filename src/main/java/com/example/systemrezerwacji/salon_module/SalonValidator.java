package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.SalonRegisterDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.systemrezerwacji.salon_module.ValidationError.*;
import static com.example.systemrezerwacji.salon_module.SalonValidationResult.*;

@Component
class SalonValidator {

    private static final String ERROR_DELIMITER = ",";
    List<ValidationError> errors;


    SalonValidationResult validate(SalonRegisterDto salonDto) {

        errors = new ArrayList<>();

        validateName(salonDto.salonName());
        validateCategory(salonDto.category());
        validateCity(salonDto.city());
        validateZipCode(salonDto.zipCode());
        validateStreet(salonDto.street());
        validateNumber(salonDto.number());


        if(errors.isEmpty()) {
            return success();
        }

        String message = errors.stream()
                .map(error -> error.message)
                .collect(Collectors.joining(ERROR_DELIMITER));
        return failure(message);

    }

    void validateName(String name) {
        if(name == null) {
            errors.add(EMPTY_NAME);
            return;
        }
        if(name.length() <= 3) {
            errors.add(SHORT_NAME);
        }

    }
    void validateCategory(String category) {
        if(category == null) {
            errors.add(EMPTY_CATEGORY);
            return;
        }
        if(category.length() <= 3) {
            errors.add(SHORT_CATEGORY);
        }
    }
    void validateCity(String city) {
        if(city == null) {
            errors.add(EMPTY_CITY);
            return;
        }
        if(!city.matches("^[a-zA-Z\\s]+$")) {
            errors.add(FORBIDDEN_CHARACTERS_IN_CITY);
        }
    }
    void validateZipCode(String zipCode) {
        if(zipCode == null) {
            errors.add(EMPTY_ZIP_CODE);
            return;
        }
        if(!zipCode.matches("\\d{2}-\\d{3}")) {
            errors.add(INCORRECT_ZIP_CODE);
        }
    }
    void validateStreet(String street) {
        if(street == null) {
            errors.add(EMPTY_STREET);
            return;
        }
        if(!street.matches("^[a-zA-Z\\s]+$")) {
            errors.add(FORBIDDEN_CHARACTERS_IN_STREET);
        }
    }
    void validateNumber(String number) {
        if(number == null) {
            errors.add(EMPTY_NUMBER);
            return;
        }
    }


}
