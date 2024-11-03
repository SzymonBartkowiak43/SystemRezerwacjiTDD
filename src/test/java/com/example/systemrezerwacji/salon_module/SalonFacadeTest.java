package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.CreatedNewSalonDto;
import com.example.systemrezerwacji.salon_module.dto.SalonFacadeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.example.systemrezerwacji.salon_module.SalonValidationResult.*;
import static com.example.systemrezerwacji.salon_module.ValidationError.*;
import static org.assertj.core.api.Assertions.assertThat;

class SalonFacadeTest {
    @Mock
    private SalonValidator validator;

    @Mock
    private SalonService salonService;

    @InjectMocks
    private SalonFacade salonFacade;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
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
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);


        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(SUCCESS_MESSAGE);
    }

    @Test
    public void should_failed_test_name_is_null() {
        //Given
        String name = null;
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = "37a";
        String userId = "1";


        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_NAME.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);

        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(EMPTY_NAME.message);
    }

    @Test
    public void should_failed_test_category_is_null() {
        //Given
        String name = "Sallon";
        String category = null;
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = "37a";
        String userId = "1";


        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_CATEGORY.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(EMPTY_CATEGORY.message);

    }
    @Test
    public void should_failed_test_city_is_null() {
        //Given
        String name = "Cat massage";
        String category = "massage";
        String city = null;
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = "37a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_CITY.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(EMPTY_CITY.message);

    }
    @Test
    public void should_failed_test_zip_code_is_null() {
        //Given
        String name = "Mesage";
        String category = "massage";
        String city = "Bialystok";
        String zipCode = null;
        String street = "Kopernika";
        String number = "37a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_ZIP_CODE.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);

        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(EMPTY_ZIP_CODE.message);

    }
    @Test
    public void should_failed_test_street_is_null() {
        //Given
        String name = "Cat message";
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = null;
        String number = "37a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_STREET.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(EMPTY_STREET.message);

    }
    @Test
    public void should_failed_test_number_is_null() {
        //Given
        String name = "Cat message";
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = null;
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_NUMBER.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(EMPTY_NUMBER.message);
    }

    @Test
    public void should_failed_test_2_fields_is_null() {
        //Given
        String name = null;
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = null;
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(EMPTY_NAME.message + " " + EMPTY_NUMBER.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).contains(EMPTY_NAME.message);
        assertThat(result.message()).contains(EMPTY_NUMBER.message);
    }

    @Test
    public void should_failed_name_is_too_short() {
        //Given
        String name = "Ca";
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = "57 a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(SHORT_NAME.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(SHORT_NAME.message);
    }

    @Test
    public void should_failed_category_is_too_short() {
        //Given
        String name = "Cat message";
        String category = "xx";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = "57 a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(SHORT_CATEGORY.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(SHORT_CATEGORY.message);
    }

    @Test
    public void should_failed_city_contains_weird_sign() {
        //Given
        String name = "Cat massage";
        String category = "massage";
        String city = "Bialy$t0k";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = "57 a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(FORBIDDEN_CHARACTERS_IN_CITY.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(FORBIDDEN_CHARACTERS_IN_CITY.message);
    }

    @Test
    public void should_failed_zip_code_is_incorrect() {
        //Given
        String name = "Cat massage";
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "X5-3";
        String street = "Kopernika";
        String number = "57 a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(INCORRECT_ZIP_CODE.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(INCORRECT_ZIP_CODE.message);
    }

    @Test
    public void should_failed_street_contains_weird_sign() {
        //Given
        String name = "Cat massage";
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "K0pe*n1ka";
        String number = "57 a";
        String userId = "1";

        SalonValidationResult validationResult = new SalonValidationResult(FORBIDDEN_CHARACTERS_IN_STREET.message);
        Mockito.when(validator.validate(Mockito.any(CreatedNewSalonDto.class))).thenReturn(validationResult);
        //When
        SalonFacadeDto result = salonFacade.createNewSalon(new CreatedNewSalonDto(name, category, city, zipCode, street, number, userId));
        //Then
        assertThat(result.message()).isEqualTo(FORBIDDEN_CHARACTERS_IN_STREET.message);
    }

}