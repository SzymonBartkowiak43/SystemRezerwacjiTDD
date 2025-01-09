package com.example.systemrezerwacji.domain.reservationModule;

import com.example.systemrezerwacji.domain.offerModule.OfferFacade;
import com.example.systemrezerwacji.domain.employeeModule.Employee;
import com.example.systemrezerwacji.domain.employeeModule.EmployeeFacade;
import com.example.systemrezerwacji.domain.employeeModule.dto.AvailableTermDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.ReservationToTomorrow;
import com.example.systemrezerwacji.infrastructure.notificationMode.NotificationFacade;
import com.example.systemrezerwacji.infrastructure.notificationMode.response.NotificationFacadeResponse;
import com.example.systemrezerwacji.domain.offerModule.Offer;
import com.example.systemrezerwacji.domain.reservationModule.dto.CreateReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.UserReservationDto;
import com.example.systemrezerwacji.domain.reservationModule.response.ReservationFacadeResponse;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.salonModule.SalonFacade;
import com.example.systemrezerwacji.domain.userModule.User;
import com.example.systemrezerwacji.domain.userModule.UserFacade;
import com.example.systemrezerwacji.domain.userModule.dto.UserCreatedWhenRegisteredDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ReservationFacade {

    private static final String PROBLEM_WITH_SENDING_NOTIFICATION = "Someone problem with sending notification ;/ ";

    private final UserFacade userFacade;
    private final OfferFacade offerFacade;
    private final SalonFacade salonFacade;
    private final EmployeeFacade employeeFacade;
    private final NotificationFacade notificationFacade;

    private final ReservationService reservationService;
    private final ReservationValidator validator;


    public ReservationFacade(@Lazy OfferFacade offerFacade, @Lazy UserFacade userFacade,
                             @Lazy SalonFacade salonFacade, @Lazy  EmployeeFacade employeeFacade,
                             @Lazy NotificationFacade notificationFacade, ReservationService reservationService, ReservationValidator validator) {
        this.offerFacade = offerFacade;
        this.userFacade = userFacade;
        this.salonFacade = salonFacade;
        this.employeeFacade = employeeFacade;
        this.notificationFacade = notificationFacade;
        this.reservationService = reservationService;
        this.validator = validator;
    }


    public List<AvailableTermDto> getEmployeeBusyTerm(Long employeeId, LocalDate date) {
        return reservationService.getEmployeeBusyTerms(employeeId, date);
    }
    @Transactional
    public ReservationFacadeResponse createNewReservation(CreateReservationDto reservationDto) {

        LocalTime duration = offerFacade.getDurationToOffer(reservationDto.offerId());

        ReservationValidationResult result = validator.validate(reservationDto, duration);

        if (result.isValid()) {
            Salon salon = salonFacade.getSalon(reservationDto.salonId());
            Employee employee = employeeFacade.getEmployee(reservationDto.employeeId());
            Offer offer = offerFacade.getOffer(reservationDto.offerId());
            UserCreatedWhenRegisteredDto userByEmailOrCreateNewAccount = userFacade.getUserByEmailOrCreateNewAccount(reservationDto.userEmail());

            User user = userByEmailOrCreateNewAccount.user();
            NotificationFacadeResponse emailRespond;
            if(!userByEmailOrCreateNewAccount.isNewUser()) {
                emailRespond = notificationFacade.sendAnEmailWhenClientHasAccount(reservationDto.userEmail(), offer.getName(), reservationDto.reservationDateTime(),salon.getSalonName());
            } else {
                emailRespond = notificationFacade.sendAnEmailWhenClientDoNotHasAccount(reservationDto.userEmail(), offer.getName(),
                        userByEmailOrCreateNewAccount.unHashedPassword(),reservationDto.reservationDateTime(),salon.getSalonName());
            }

            if(emailRespond.isSuccess()) {
                reservationService.addNewReservation(salon, employee, user, offer, reservationDto.reservationDateTime());
                return new ReservationFacadeResponse(true, "success", userByEmailOrCreateNewAccount.unHashedPassword());
            }

            return new ReservationFacadeResponse(false, PROBLEM_WITH_SENDING_NOTIFICATION, null);
        }
        return new ReservationFacadeResponse(false, result.message(),null);
    }

    public List<UserReservationDto> getUserReservation(String email) {
        User user = userFacade.getUserByEmail(email);

        List<UserReservationDto> userReservationDtoList = reservationService.getReservationToCurrentUser(user);

        return userReservationDtoList;
    }

    public List<ReservationToTomorrow> getAllReservationToTomorrow() {
        List<Reservation> allReservationToTomorrow = reservationService.getAllReservationToTomorrow();

        return allReservationToTomorrow.stream()
                .map(reservation -> new ReservationToTomorrow(
                        reservation.getSalon().getId(),
                        reservation.getUser().getId(),
                        reservation.getOffer().getId(),
                        reservation.getReservationDateTime()
                ))
                .toList();
    }
}
