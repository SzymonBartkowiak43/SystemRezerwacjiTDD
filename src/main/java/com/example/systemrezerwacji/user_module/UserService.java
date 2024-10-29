package com.example.systemrezerwacji.user_module;

import com.example.systemrezerwacji.user_module.dto.UserRegisterDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserService {
    private static final String DEFAULT_USER_ROLE = "USER";
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


    private UserRole getDefaultRole() {
        return userRoleRepository.findByName(DEFAULT_USER_ROLE).orElseThrow();
    }
}
