package com.example.systemrezerwacji.salon_module;

import com.example.systemrezerwacji.salon_module.dto.SalonFacadeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.systemrezerwacji.salon_module.SalonValidationResult.*;
import static com.example.systemrezerwacji.salon_module.ValidationError.*;
import static org.assertj.core.api.Assertions.assertThat;

class SalonFacadeTest {
    private SalonFacade salonFacade;

    @BeforeEach
    void init() {
        salonFacade = new SalonFacade();
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

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
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

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
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

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
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

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
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

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
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

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
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

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
        //Then
        assertThat(result.message()).isEqualTo(EMPTY_NUMBER.message);
    }

    @Test
    public void should_falied_test_2_fields_is_null() {
        //Given
        String name = null;
        String category = "massage";
        String city = "Bialystok";
        String zipCode = "15-376";
        String street = "Kopernika";
        String number = null;

        //When
        SalonFacadeDto result = salonFacade.inputStrings(name, category, city, zipCode, street, number);
        //Then
        assertThat(result.message()).contains(EMPTY_NAME.message);
        assertThat(result.message()).contains(EMPTY_NUMBER.message);
    }


}