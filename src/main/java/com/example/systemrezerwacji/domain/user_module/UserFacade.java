package com.example.systemrezerwacji.domain.user_module;


import com.example.systemrezerwacji.domain.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserCreatedWhenRegisteredDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserRoleDto;
import com.example.systemrezerwacji.domain.user_module.response.UserFacadeResponse;
import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import lombok.Builder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.systemrezerwacji.domain.user_module.UserValidationResult.SUCCESS_MESSAGE;

@Component
public class UserFacade {
    private static final String USER_NOT_FOUND = "Not found";
    private final UserService userService;
    private final UserValidator validator;

    public UserFacade(UserService userService, UserValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    public UserDto findByEmail(String email) {
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND));


        Set<UserRoleDto> userRoleDtos = user.getRoles().stream()
                .map(role -> new UserRoleDto(role.getName()))
                .collect(Collectors.toSet());


        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(userRoleDtos)
                .build();
    }

    public UserFacadeResponse createNewUser(UserRegisterDto userDto) {
        UserValidationResult validate = validator.validate(userDto);
        String message = validate.validationMessage();

        if(!message.equals(SUCCESS_MESSAGE)) {
            return new UserFacadeResponse(message, null, null);
        }

        User user = userService.createNewUser(userDto);

        return new UserFacadeResponse(message,user.getId(), user.getName());
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
