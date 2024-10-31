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
import static com.example.systemrezerwacji.user_module.ValidationError.*;
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

    @Test
    public void should_failed_name_is_null() {
        //Given
        String name = null;
        String email = "kuba@test.pl";
        String password = "kuba123@";

        UserValidationResult validationResult = new UserValidationResult(EMPTY_NAME.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(EMPTY_NAME.getMessage());
    }

    @Test
    public void should_failed_email_is_null() {
        //Given
        String name = "Kuba";
        String email = null;
        String password = "kuba123@";

        UserValidationResult validationResult = new UserValidationResult(EMPTY_EMAIL.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(EMPTY_EMAIL.getMessage());
    }

    @Test
    public void should_failed_password_is_null() {
        //Given
        String name = "Kuba";
        String email = "kuba@test.pl";
        String password = null;

        UserValidationResult validationResult = new UserValidationResult(EMPTY_PASSWORD.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(EMPTY_PASSWORD.getMessage());
    }

    @Test
    public void should_failed_name_is_to_short() {
        //Given
        String name = "xx";
        String email = "kuba@test.pl";
        String password = "Kuba123@";

        UserValidationResult validationResult = new UserValidationResult(SHORT_NAME.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(SHORT_NAME.getMessage());
    }

    @Test
    public void should_failed_name_contains_weird_sign() {
        //Given
        String name = "$#%#KAW";
        String email = "kuba@test.pl";
        String password = "Kuba123@";

        UserValidationResult validationResult = new UserValidationResult(FORBIDDEN_CHARACTERS_IN_NAME.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(FORBIDDEN_CHARACTERS_IN_NAME.getMessage());
    }

    @Test
    public void should_failed_email_is_invalid() {
        //Given
        String name = "Kuba";
        String email = "kubatest.pl";
        String password = "Kuba123@";

        UserValidationResult validationResult = new UserValidationResult(INVALID_EMAIL.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(INVALID_EMAIL.getMessage());
    }

    @Test
    public void should_failed_password_is_to_short() {
        //Given
        String name = "Kuba";
        String email = "kubatest.pl";
        String password = "Ku@1";

        UserValidationResult validationResult = new UserValidationResult(SHORT_PASSWORD.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(SHORT_PASSWORD.getMessage());
    }

    @Test
    public void should_failed_password_do_not_contains_number() {
        //Given
        String name = "Kuba";
        String email = "kubatest.pl";
        String password = "Kuba@";

        UserValidationResult validationResult = new UserValidationResult(NO_NUMBER_IN_EMAIL.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(NO_NUMBER_IN_EMAIL.getMessage());
    }

    @Test
    public void should_failed_password_do_not_contains_special_characters() {
        //Given
        String name = "Kuba";
        String email = "kubatest.pl";
        String password = "Kuba@";

        UserValidationResult validationResult = new UserValidationResult(NO_SPECIAL_CHARACTERS_IN_PASSWORD.getMessage());
        Mockito.when(validator.validate(Mockito.any(UserRegisterDto.class))).thenReturn(validationResult);

        //When
        UserFacadeDto result = userFacade.createNewUser(new UserRegisterDto(name,email,password));

        //Then
        assertThat(result.message()).isEqualTo(NO_SPECIAL_CHARACTERS_IN_PASSWORD.getMessage());
    }






}