package com.example.systemrezerwacji.infrastructure.restControllers;


import com.example.systemrezerwacji.domain.userModule.UserFacade;
import com.example.systemrezerwacji.domain.userModule.response.UserFacadeResponse;
import com.example.systemrezerwacji.domain.userModule.dto.UserRegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserController {
    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/user")
    public ResponseEntity<UserFacadeResponse> createUser(@RequestBody UserRegisterDto userDto) {
        UserFacadeResponse newUser = userFacade.createNewUser(userDto);

        if(newUser.userId() == null) {
            return ResponseEntity.badRequest().body(newUser);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.userId())
                .toUri();

        return ResponseEntity.created(location).body(newUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserRegisterDto> getUser(@PathVariable Integer id) {
        return userFacade.getUserByid(id.longValue())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
