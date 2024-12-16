package com.example.systemrezerwacji.infrastructure.loginandregister;

import com.example.systemrezerwacji.domain.user_module.UserFacade;
import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import com.example.systemrezerwacji.domain.user_module.response.UserFacadeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final UserFacade userFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserFacadeResponse> register(@RequestBody UserRegisterDto registerUserDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(registerUserDto.password());
        UserFacadeResponse registerResult = userFacade.createNewUser(
                new UserRegisterDto(registerUserDto.email(), registerUserDto.name(), encodedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResult);
    }
}
