package com.example.systemrezerwacji.domain.reservation_module;

import com.example.systemrezerwacji.domain.offer_module.OfferFacade;
import com.example.systemrezerwacji.domain.employee_module.Employee;
import com.example.systemrezerwacji.domain.employee_module.EmployeeFacade;
import com.example.systemrezerwacji.domain.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.infrastructure.notification_mode.NotificationFacade;
import com.example.systemrezerwacji.infrastructure.notification_mode.response.NotificationFacadeResponse;
import com.example.systemrezerwacji.domain.offer_module.Offer;
import com.example.systemrezerwacji.domain.reservation_module.dto.CreateReservationDto;
import com.example.systemrezerwacji.domain.reservation_module.dto.UserReservationDto;
import com.example.systemrezerwacji.domain.reservation_module.response.ReservationFacadeResponse;
import com.example.systemrezerwacji.domain.salon_module.Salon;
import com.example.systemrezerwacji.domain.salon_module.SalonFacade;
import com.example.systemrezerwacji.domain.user_module.User;
import com.example.systemrezerwacji.domain.user_module.UserFacade;
import com.example.systemrezerwacji.domain.user_module.dto.UserCreatedWhenRegisteredDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;


    public ReservationFacade(@Lazy OfferFacade offerFacade, @Lazy UserFacade userFacade,
                             @Lazy SalonFacade salonFacade, @Lazy  EmployeeFacade employeeFacade,
                             NotificationFacade notificationFacade, ReservationService reservationService, ReservationValidator validator, PasswordEncoder passwordEncoder) {
        this.offerFacade = offerFacade;
        this.userFacade = userFacade;
        this.salonFacade = salonFacade;
        this.employeeFacade = employeeFacade;
        this.notificationFacade = notificationFacade;
        this.reservationService = reservationService;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
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
                emailRespond = notificationFacade.sendAnEmailWhenClientHasAccount(reservationDto.userEmail(), offer.getName(), reservationDto.reservationDateTime());
            } else {
                emailRespond = notificationFacade.sendAnEmailWhenClientDoNotHasAccount(reservationDto.userEmail(), offer.getName(),
                        userByEmailOrCreateNewAccount.unHashedPassword(),reservationDto.reservationDateTime());
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
}
