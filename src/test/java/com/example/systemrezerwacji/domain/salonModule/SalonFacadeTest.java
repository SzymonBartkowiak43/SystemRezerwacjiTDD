package com.example.systemrezerwacji.domain.salonModule;

import com.example.systemrezerwacji.domain.codeModule.Code;
import com.example.systemrezerwacji.domain.codeModule.CodeFacade;
import com.example.systemrezerwacji.domain.codeModule.message.ConsumeMessage;
import com.example.systemrezerwacji.domain.salonModule.dto.CreateNewSalonDto;
import com.example.systemrezerwacji.domain.salonModule.dto.SalonFacadeResponseDto;
import com.example.systemrezerwacji.domain.userModule.User;
import com.example.systemrezerwacji.domain.userModule.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.example.systemrezerwacji.domain.salonModule.SalonValidationResult.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SalonFacadeTest {
    @Mock
    private SalonValidator validator;

    @Mock
    private SalonService salonService;

    @Mock
    private UserFacade userFacade;

    @Mock
    SalonCreator salonCreator;

    @Mock
    private CodeFacade codeFacade;

    @InjectMocks
    private SalonFacade salonFacade;

    private Code code;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        code = new Code();
    }

    @Test
    public void should_create_salon() {
        //Given
        String name = "Cat massage";
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = "37a";
        String userId = "1";


        SalonValidationResult validationResult = new SalonValidationResult(SUCCESS_MESSAGE);
        ConsumeMessage consumeMessage1 = mock(ConsumeMessage.class);
        when(consumeMessage1.isSuccess()).thenReturn(true);

        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
        when(userFacade.getUserWithId(Long.valueOf(userId))).thenReturn(Optional.of(new User()));
        when(codeFacade.consumeCode(eq(code.getCode()), any(User.class))).thenReturn(consumeMessage1);


        //When
        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId,code.getCode()));
        //Then
        assertThat(result.message()).isEqualTo(SUCCESS_MESSAGE);
    }

//    @Test
//    public void should_failed_test_name_is_null() {
//        //Given
//        String name = null;
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = "37a";
//        String userId = "1";
//
//
//        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_NAME.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(EMPTY_NAME.message);
//    }
//
//    @Test
//    public void should_failed_test_category_is_null() {
//        //Given
//        String name = "Sallon";
//        String category = null;
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = "37a";
//        String userId = "1";
//
//
//        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_CATEGORY.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(EMPTY_CATEGORY.message);
//
//    }
//    @Test
//    public void should_failed_test_city_is_null() {
//        //Given
//        String name = "Cat massage";
//        String category = "massage";
//        String city = null;
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = "37a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_CITY.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(EMPTY_CITY.message);
//
//    }
//    @Test
//    public void should_failed_test_zip_code_is_null() {
//        //Given
//        String name = "Mesage";
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = null;
//        String street = "Kopernika";
//        String number = "37a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_ZIP_CODE.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(EMPTY_ZIP_CODE.message);
//
//    }
//    @Test
//    public void should_failed_test_street_is_null() {
//        //Given
//        String name = "Cat message";
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = null;
//        String number = "37a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_STREET.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(EMPTY_STREET.message);
//
//    }
//    @Test
//    public void should_failed_test_number_is_null() {
//        //Given
//        String name = "Cat message";
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = null;
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_NUMBER.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(EMPTY_NUMBER.message);
//    }
//
//    @Test
//    public void should_failed_test_2_fields_is_null() {
//        //Given
//        String name = null;
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = null;
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_NAME.message + " " + EMPTY_NUMBER.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).contains(EMPTY_NAME.message);
//        assertThat(result.message()).contains(EMPTY_NUMBER.message);
//    }
//
//    @Test
//    public void should_failed_name_is_too_short() {
//        //Given
//        String name = "Ca";
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = "57 a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(SHORT_NAME.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(SHORT_NAME.message);
//    }
//
//    @Test
//    public void should_failed_category_is_too_short() {
//        //Given
//        String name = "Cat message";
//        String category = "xx";
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = "57 a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(SHORT_CATEGORY.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(SHORT_CATEGORY.message);
//    }
//
//    @Test
//    public void should_failed_city_contains_weird_sign() {
//        //Given
//        String name = "Cat massage";
//        String category = "massage";
//        String city = "Bialy$t0k";
//        String zipCode = "15-376";
//        String street = "Kopernika";
//        String number = "57 a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(FORBIDDEN_CHARACTERS_IN_CITY.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(FORBIDDEN_CHARACTERS_IN_CITY.message);
//    }
//
//    @Test
//    public void should_failed_zip_code_is_incorrect() {
//        //Given
//        String name = "Cat massage";
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = "X5-3";
//        String street = "Kopernika";
//        String number = "57 a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(INCORRECT_ZIP_CODE.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(INCORRECT_ZIP_CODE.message);
//    }
//
//    @Test
//    public void should_failed_street_contains_weird_sign() {
//        //Given
//        String name = "Cat massage";
//        String category = "massage";
//        String city = "Bialystok";
//        String zipCode = "15-376";
//        String street = "K0pe*n1ka";
//        String number = "57 a";
//        String userId = "1";
//
//        SalonValidationResult validationResult = new SalonValidationResult(FORBIDDEN_CHARACTERS_IN_STREET.message);
//        when(validator.validate(any(CreateNewSalonDto.class))).thenReturn(validationResult);
//        //When
//        SalonFacadeResponseDto result = salonFacade.createNewSalon(new CreateNewSalonDto(name, category, city, zipCode, street, number, userId, code.getCode()));
//        //Then
//        assertThat(result.message()).isEqualTo(FORBIDDEN_CHARACTERS_IN_STREET.message);
//    }

}