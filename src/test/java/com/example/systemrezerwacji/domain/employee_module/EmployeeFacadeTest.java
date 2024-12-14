package com.example.systemrezerwacji.domain.employee_module;

import com.example.systemrezerwacji.domain.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.employee_module.response.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.domain.offer_module.OfferFacade;
import com.example.systemrezerwacji.domain.reservation_module.ReservationFacade;
import com.example.systemrezerwacji.domain.salon_module.Salon;
import com.example.systemrezerwacji.domain.user_module.User;
import com.example.systemrezerwacji.domain.user_module.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Adnotacja do obsługi Mockito
class EmployeeFacadeTest {

    EmployeeRepository employeeRepository = new EmployeeRepositoryTestImpl();
    EmployeeAvailabilityRepository employeeAvailabilityRepository = new EmployeeAvailabilityRepositoryTestImpl();

    // Konfiguracja moków
    @Mock
    UserFacade userFacade;

    @Mock
    OfferFacade offerFacade;

    @Mock
    ReservationFacade reservationFacade;

    EmployeeFacade employeeFacade;

    @BeforeEach
    void setUp() {
        // Inicjalizacja EmployeeConfiguration z mokami
        EmployeeConfiguration configuration = new EmployeeConfiguration();
        configuration.userFacade = userFacade; // Podstawienie moków
        configuration.offerFacade = offerFacade;
        configuration.reservationFacade = reservationFacade;
        employeeFacade = configuration.createForTest(employeeRepository, employeeAvailabilityRepository);
    }

    @Test
    void should_create_new_employee_and_add_this_to_salon() {
        // given
        Salon salon = new Salon();
        EmployeeDto employeeDto = EmployeeDto.builder()
                .email("employee@em.pl")
                .name("Employee Employee")
                .salonId(1L)
                .availability(List.of()).build();

        when(userFacade.createEmployee(any(EmployeeDto.class)))
                .thenReturn(Optional.of(new User()));

        // when
        CreateEmployeeResponseDto responseDto = employeeFacade.createEmployeeAndAddToSalon(employeeDto, salon);

        // then
        assertThat(responseDto.message()).isEqualTo("success");
        verify(userFacade).createEmployee(any(EmployeeDto.class));
    }
}
