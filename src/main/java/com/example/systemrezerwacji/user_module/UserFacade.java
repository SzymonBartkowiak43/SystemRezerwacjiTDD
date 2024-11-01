package com.example.systemrezerwacji.user_module;


import com.example.systemrezerwacji.user_module.dto.UserFacadeDto;
import com.example.systemrezerwacji.user_module.dto.UserRegisterDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.systemrezerwacji.user_module.UserValidationResult.SUCCESS_MESSAGE;

@Component
public class UserFacade {
    private final UserService userService;
    private final UserValidator validator;

    public UserFacade(UserService userService, UserValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    public UserFacadeDto createNewUser(UserRegisterDto userDto) {
        UserValidationResult validate = validator.validate(userDto);
        String message = validate.validationMessage();

        if(!message.equals(SUCCESS_MESSAGE)) {
            return new UserFacadeDto(message, null);
        }

        Long newUserId = userService.createNewUser(userDto);

        return new UserFacadeDto(message,newUserId);
    }

    public Optional<UserRegisterDto> getUserByid(Long id) {
        return userService.getUser(id);
    }

}
