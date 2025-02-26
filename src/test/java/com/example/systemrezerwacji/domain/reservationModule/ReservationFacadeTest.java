package com.example.systemrezerwacji.domain.reservationModule;

import com.example.systemrezerwacji.domain.employeeModule.Employee;
import com.example.systemrezerwacji.domain.employeeModule.EmployeeFacade;
import com.example.systemrezerwacji.domain.employeeModule.dto.AvailableTermDto;
import com.example.systemrezerwacji.domain.employeeModule.dto.AvailableTermWithDateDto;
import com.example.systemrezerwacji.domain.offerModule.Offer;
import com.example.systemrezerwacji.domain.offerModule.OfferFacade;
import com.example.systemrezerwacji.domain.reservationModule.dto.*;
import com.example.systemrezerwacji.domain.reservationModule.response.ReservationFacadeResponse;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import com.example.systemrezerwacji.domain.userModule.User;
import com.example.systemrezerwacji.domain.userModule.UserFacade;
import com.example.systemrezerwacji.domain.userModule.dto.UserCreatedWhenRegisteredDto;
import com.example.systemrezerwacji.infrastructure.notificationMode.NotificationFacade;
import com.example.systemrezerwacji.infrastructure.notificationMode.response.NotificationFacadeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationFacadeTest {

    ReservationRepository reservationRepository = new ReservationRepositoryTestImpl();

    @Mock
    private UserFacade userFacade;

    @Mock
    private OfferFacade offerFacade;

    @Mock
    private SalonFacade salonFacade;

    @Mock
    private EmployeeFacade employeeFacade;

    @Mock
    private NotificationFacade notificationFacade;

    ReservationFacade reservationFacade;

    private final LocalDateTime testDateTime = LocalDateTime.of(2026,2,2,12,0);
    private final String testEmail = "test@example.com";
    private final Long testSalonId = 1L;
    private final Long testOfferId = 1L;
    private final Long testEmployeeId = 1L;

    @BeforeEach
    void setUp() {
        ReservationConfiguration configuration = new ReservationConfiguration();
        configuration.userFacade = userFacade;
        configuration.offerFacade = offerFacade;
        configuration.salonFacade = salonFacade;
        configuration.employeeFacade = employeeFacade;
        configuration.notificationFacade = notificationFacade;
        reservationFacade = configuration.createForTest(reservationRepository);
    }

    @Test
    void shouldReturnValidationErrorWhenInvalidReservation() {
        // given
        CreateReservationDto dto = new CreateReservationDto(
                testSalonId,
                testEmployeeId,
                testOfferId,
                LocalDateTime.of(2027,2,2,12,0),
                testEmail
        );

        // when
        when(offerFacade.getDurationToOffer(anyLong())).thenReturn(LocalTime.of(1, 0));

        ReservationFacadeResponse response = reservationFacade.createNewReservation(dto);

        // then
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.message()).isEqualTo("Invalid data");
    }

    @Test
    void shouldReturnFailureWhenNotificationFails() {
        // given
        CreateReservationDto dto = createTestReservationDto();
        User testUser = new User();
        Offer testOffer = new Offer();
        Salon testSalon = new Salon();

        when(salonFacade.getSalon(anyLong())).thenReturn(testSalon);
        when(employeeFacade.getEmployee(anyLong())).thenReturn(new Employee());
        when(offerFacade.getOffer(anyLong())).thenReturn(testOffer);
        when(userFacade.getUserByEmailOrCreateNewAccount(anyString()))
                .thenReturn(new UserCreatedWhenRegisteredDto(testUser, false, ""));
        when(notificationFacade.sendAnEmailWhenClientHasAccount(any(), any(), any(), any()))
                .thenReturn(new NotificationFacadeResponse(false));

        // when
        ReservationFacadeResponse response = reservationFacade.createNewReservation(dto);

        // then
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.message()).contains("notification");
    }

    @Test
    void shouldCreateReservationForExistingUser() {
        // given
        CreateReservationDto dto = createTestReservationDto();
        User testUser = new User();
        testUser.setEmail(testEmail);

        when(salonFacade.getSalon(anyLong())).thenReturn(new Salon());
        when(employeeFacade.getEmployee(anyLong())).thenReturn(new Employee());
        when(offerFacade.getOffer(anyLong())).thenReturn(new Offer());
        when(userFacade.getUserByEmailOrCreateNewAccount(anyString()))
                .thenReturn(new UserCreatedWhenRegisteredDto(testUser, false, null));
        when(notificationFacade.sendAnEmailWhenClientHasAccount(any(), any(), any(), any()))
                .thenReturn(new NotificationFacadeResponse(true));

        // when
        ReservationFacadeResponse response = reservationFacade.createNewReservation(dto);

        // then
        assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void shouldReturnUserReservations() {
        // given
        User testUser = new User();

        when(userFacade.getUserByEmail(testEmail)).thenReturn(testUser);

        // when
        List<UserReservationDataDto> result = reservationFacade.getUserReservation(testEmail);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void shouldDeleteReservationSuccessfully() {
        // given
        DeleteReservationDto dto = new DeleteReservationDto(1L, testEmail);
        User testUser = new User();

        when(userFacade.getUserByEmail(testEmail)).thenReturn(testUser);

        // when
        ReservationFacadeResponse response = reservationFacade.deleteReservation(dto);

        // then
        assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void shouldReturnNearest5AvailableTerms() {
        // given
        Long reservationId = 1L;
        Reservation testReservation = createTestReservation();

        when(employeeFacade.getAvailableHours(any())).thenReturn(
                List.of(
                        new AvailableTermDto(LocalTime.of(9, 0), LocalTime.of(10, 0)),
                        new AvailableTermDto(LocalTime.of(10, 0), LocalTime.of(11, 0))
                )
        );

        // when
        List<AvailableTermWithDateDto> result = reservationFacade.getNearest5AvailableHours(reservationId);

        // then
        assertThat(result).hasSize(5);
        assertThat(result)
                .extracting(AvailableTermWithDateDto::date)
                .contains(testReservation.getReservationDateTime().toLocalDate());
    }

    private CreateReservationDto createTestReservationDto() {
        return new CreateReservationDto(
                testSalonId,
                testEmployeeId,
                testOfferId,
                testDateTime,
                testEmail
        );
    }

    private Reservation createTestReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationDateTime(testDateTime);
        reservation.setEmployee(new Employee());
        reservation.setOffer(new Offer());
        return reservation;
    }
}