package com.example.systemrezerwacji.user_module;

import com.example.systemrezerwacji.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.user_module.dto.UserRegisterDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserService {
    private static final String DEFAULT_USER_ROLE = "USER";
    private static final String OWNER_ROLE = "OWNER";
    private static final String EMPLOYEE_ROLE = "EMPLOYEE";
    private static final String ADMIN_ROLE = "ADMIN";

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final MaperUserToUserRegisterDto mapper;

    UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, MaperUserToUserRegisterDto mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    Long createNewUser(UserRegisterDto userDto) {
        User user = new User.UserBuilder()
                .addName(userDto.name())
                .addEmail(userDto.email())
                .addPassword(userDto.password())
                .addUserRole(getDefaultRole())
                .build();

        userRepository.save(user);
        return user.getId();
    }
    Optional<UserRegisterDto> getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser.map(mapper::map);

    }



    Optional<User> getUserWithId(Long id) {
        return userRepository.findById(id);
    }

    Optional<User> addRoleOwner(Long id) {
        UserRole userRole = userRoleRepository.findByName(OWNER_ROLE).orElseThrow();
        Optional<User> userWithId = getUserWithId(id);

        User user = userWithId.get().addUserRole(userRole);
        userRepository.save(user);

        return userWithId;
    }

    Optional<User> createEmployee(EmployeeDto employeeDto) {

        String password = PasswordGenerator.generatePassword();
        UserRegisterDto userRegister = new UserRegisterDto(employeeDto.email(), employeeDto.name(), password);

        Long newUser = createNewUser(userRegister);
        Optional<User> employee = addRoleEmployee(newUser);

        return employee;
    }

    String getNameByid(Long id) {
        User userById = userRepository.getUserById(id);
        return userById.getName();
    }



    private UserRole getDefaultRole() {
        return userRoleRepository.findByName(DEFAULT_USER_ROLE).orElseThrow();
    }

    private  Optional<User> addRoleEmployee(Long id) {
        UserRole userRole = userRoleRepository.findByName(EMPLOYEE_ROLE).orElseThrow();
        Optional<User> userWithId = getUserWithId(id);

        User user = userWithId.get().addUserRole(userRole);
        userRepository.save(user);

        return userWithId;
    }
}
