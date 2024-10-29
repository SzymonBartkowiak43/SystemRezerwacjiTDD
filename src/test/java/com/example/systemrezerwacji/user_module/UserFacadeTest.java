package com.example.systemrezerwacji.user_module;

import com.example.systemrezerwacji.user_module.dto.UserFacadeDto;
import com.example.systemrezerwacji.user_module.dto.UserRegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.example.systemrezerwacji.user_module.UserValidationResult.*;
import static org.assertj.core.api.Assertions.assertThat;


class UserFacadeTest {

    @Mock
    private UserValidator validator;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacade userFacade;

    @BeforeEach
    void inti() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void should_create_user() {
        //Given
        String name = "Kuba";
        String email = "kuba@test.pl";
        String password = "kuba123@";

        UserValidationResult validationResult = new UserValidationResult(SUCCESS_MESSAGE);
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(SUCCESS_MESSAGE);
    }
}