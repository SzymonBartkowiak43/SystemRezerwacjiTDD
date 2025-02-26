package com.example.systemrezerwacji.domain.reservationModule;


import com.example.systemrezerwacji.domain.employeeModule.EmployeeFacade;
import com.example.systemrezerwacji.domain.offerModule.OfferFacade;
import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import com.example.systemrezerwacji.domain.userModule.UserFacade;
import com.example.systemrezerwacji.infrastructure.notificationMode.NotificationFacade;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReservationConfiguration {

    @Mock
    UserFacade userFacade;

    @Mock
    OfferFacade offerFacade;

    @Mock
    SalonFacade salonFacade;

    @Mock
    EmployeeFacade employeeFacade;

    @Mock
    NotificationFacade notificationFacade;

    public ReservationConfiguration() {
        MockitoAnnotations.openMocks(this);
    }

    ReservationFacade createForTest(ReservationRepository reservationRepository) {
        MapperReservationDto mapperReservationDto = new MapperReservationDto();
        ReservationService reservationService = new ReservationService(reservationRepository, mapperReservationDto);
        ReservationValidator validator = new ReservationValidator(reservationService);
        return new ReservationFacade(offerFacade, userFacade, salonFacade, employeeFacade, notificationFacade, reservationService, validator);
    }
}
