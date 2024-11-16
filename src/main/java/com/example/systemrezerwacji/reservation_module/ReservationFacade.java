package com.example.systemrezerwacji.reservation_module;

import com.example.systemrezerwacji.employee_module.Employee;
import com.example.systemrezerwacji.employee_module.EmployeeFacade;
import com.example.systemrezerwacji.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.offer_module.Offer;
import com.example.systemrezerwacji.offer_module.OfferFacade;
import com.example.systemrezerwacji.reservation_module.dto.CreateReservationDto;
import com.example.systemrezerwacji.reservation_module.dto.ReservationFacadeResponse;
import com.example.systemrezerwacji.reservation_module.dto.ReservationValidationResult;
import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.salon_module.SalonFacade;
import com.example.systemrezerwacji.user_module.User;
import com.example.systemrezerwacji.user_module.UserFacade;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ReservationFacade {

    private final UserFacade userFacade;
    private final OfferFacade offerFacade;
    private final SalonFacade salonFacade;
    private final EmployeeFacade employeeFacade;

    private final ReservationService reservationService;
    private final ReservationValidator validator;

    public ReservationFacade(@Lazy OfferFacade offerFacade, @Lazy UserFacade userFacade,
                             @Lazy SalonFacade salonFacade,@Lazy  EmployeeFacade employeeFacade,
                             ReservationService reservationService,  ReservationValidator validator) {
        this.offerFacade = offerFacade;
        this.userFacade = userFacade;
        this.salonFacade = salonFacade;
        this.employeeFacade = employeeFacade;
        this.reservationService = reservationService;
        this.validator = validator;
    }


    public List<AvailableTermDto> getEmployeeBusyTerm(Long employeeId, LocalDate date) {
        return reservationService.getEmployeeBusyTerms(employeeId, date);
    }

    public ReservationFacadeResponse createNewReservation(CreateReservationDto reservationDto) {
        LocalTime duration = offerFacade.getDurationToOffer(reservationDto.offerId());

        ReservationValidationResult result = validator.validate(reservationDto, duration);

        if (result.isValid()) {
            User user = userFacade.getUserToOffer(reservationDto.userEmail());

            Salon salon = salonFacade.getSalon(reservationDto.salonId());
            Employee employee = employeeFacade.getEmployee(reservationDto.employeeId());
            Offer offer = offerFacade.getOffer(reservationDto.offerId());

            reservationService.addNewReservation(salon, employee, user, offer, reservationDto.reservationDateTime());
            return new ReservationFacadeResponse(true, "success");
        }
        return new ReservationFacadeResponse(false, "someone error :?C");
    }
}
