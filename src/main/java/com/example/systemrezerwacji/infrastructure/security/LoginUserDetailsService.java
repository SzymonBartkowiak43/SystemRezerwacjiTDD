package com.example.systemrezerwacji.infrastructure.security;


import com.example.systemrezerwacji.domain.user_module.UserFacade;
import com.example.systemrezerwacji.domain.user_module.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
        UserDto userDto = userFacade.findByEmail(email);
        return getUser(userDto);
    }


    private org.springframework.security.core.userdetails.User getUser(UserDto user) {
        return new org.springframework.security.core.userdetails.User(
                user.email(),
                user.password(),
                user.roles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .toList()
        );
    }

}
