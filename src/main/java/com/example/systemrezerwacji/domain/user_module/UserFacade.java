package com.example.systemrezerwacji.domain.user_module;


import com.example.systemrezerwacji.domain.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserCreatedWhenRegisteredDto;
import com.example.systemrezerwacji.domain.user_module.response.UserFacadeResponse;
import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.systemrezerwacji.domain.user_module.UserValidationResult.SUCCESS_MESSAGE;

@Component
public class UserFacade {
    private final UserService userService;
    private final UserValidator validator;

    public UserFacade(UserService userService, UserValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    public UserFacadeResponse createNewUser(UserRegisterDto userDto) {
        UserValidationResult validate = validator.validate(userDto);
        String message = validate.validationMessage();

        if(!message.equals(SUCCESS_MESSAGE)) {
            return new UserFacadeResponse(message, null);
        }

        Long newUserId = userService.createNewUser(userDto);

        return new UserFacadeResponse(message,newUserId);
    }

    public Optional<UserRegisterDto> getUserByid(Long id) {
        return userService.getUser(id);
    }

    public Optional<User> getUserToCreateSalon(Long id) {
        return userService.getUserWithId(id);
    }

    public Optional<User> addUserRoleOwner(Long id) {
        return userService.addRoleOwner(id);
    }

    public Optional<User> createEmployee(EmployeeDto employeeDto) {
        return userService.createEmployee(employeeDto);
    }


    public Map<Long, String> getEmployeeNameById(List<Long> employessId) {
        return employessId.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        userService::getNameById
                ));
    }

    public UserCreatedWhenRegisteredDto getUserByEmailOrCreateNewAccount(String email) {
        return userService.getUserByEmailOrCreateNewAccount(email);
    }
}
