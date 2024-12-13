package com.example.systemrezerwacji.domain.user_module;


import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import org.springframework.stereotype.Component;

@Component
class MaperUserToUserRegisterDto {

    UserRegisterDto map(User user) {
        if(user == null) {
            return null;
        }

        return new UserRegisterDto(
                user.getEmail(),
                user.getName(),
                user.getPassword()

        );
    }
}
