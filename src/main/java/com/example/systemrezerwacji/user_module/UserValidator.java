package com.example.systemrezerwacji.user_module;


import com.example.systemrezerwacji.user_module.dto.UserRegisterDto;
import org.springframework.stereotype.Component;


import static com.example.systemrezerwacji.user_module.UserValidationResult.success;
import static com.example.systemrezerwacji.user_module.ValidationError.*;
import java.util.List;

@Component
class UserValidator {
    private static final String ERROR_DELIMITER = ",";
    private List<ValidationError> errors;

    UserValidationResult validate(UserRegisterDto user) {
        return success();
    }
}
