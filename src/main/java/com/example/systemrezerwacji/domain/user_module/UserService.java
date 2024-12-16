package com.example.systemrezerwacji.domain.user_module;

import com.example.systemrezerwacji.domain.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import com.example.systemrezerwacji.domain.user_module.dto.UserCreatedWhenRegisteredDto;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserService {

    private static final String DEFAULT_USER_ROLE = "USER";
    private static final String OWNER_ROLE = "OWNER";
    private static final String EMPLOYEE_ROLE = "EMPLOYEE";
    private static final String ADMIN_ROLE = "ADMIN";
    public static final String GUEST = "Guest";

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final MaperUserToUserRegisterDto mapper;

    UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, MaperUserToUserRegisterDto mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    User createNewUser(UserRegisterDto userDto) {
        User user = createUser(userDto);
        return user;
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

        User newUser = createNewUser(userRegister);
        Optional<User> employee = addRoleEmployee(newUser.getId());

        return employee;
    }

    String getNameById(Long id) {
        User userById = userRepository.getUserById(id);
        return userById.getName();
    }

    Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    UserCreatedWhenRegisteredDto getUserByEmailOrCreateNewAccount(String email) {
        Optional<User> userByEmail = userRepository.getUserByEmail(email);

        if(userByEmail.isPresent()) {
            return new UserCreatedWhenRegisteredDto(userByEmail.get(),false);
        }
        User newUser = createNewUserByEmail(email);

        return new UserCreatedWhenRegisteredDto(newUser, true);
    }

    private User createUser(UserRegisterDto userDto) {
        User user = new User.UserBuilder()
                .addName(userDto.name())
                .addEmail(userDto.email())
                .addPassword(userDto.password())
                .addUserRole(getDefaultRole())
                .build();

        userRepository.save(user);
        return user;
    }

    private UserRole getDefaultRole() {
        return userRoleRepository
                .findByName(DEFAULT_USER_ROLE)
                .orElseThrow(() -> new BadCredentialsException("User Role not Exists!"));
    }

    private Optional<User> addRoleEmployee(Long id) {
        UserRole userRole = userRoleRepository.findByName(EMPLOYEE_ROLE).orElseThrow();
        Optional<User> userWithId = getUserWithId(id);

        User user = userWithId.get().addUserRole(userRole);
        userRepository.save(user);

        return userWithId;
    }

    private User createNewUserByEmail(String email) {
        String password = PasswordGenerator.generatePassword();
        UserRegisterDto userRegisterDto = new UserRegisterDto(email, GUEST, password);
        User user = createUser(userRegisterDto);
        return user;
    }


}
