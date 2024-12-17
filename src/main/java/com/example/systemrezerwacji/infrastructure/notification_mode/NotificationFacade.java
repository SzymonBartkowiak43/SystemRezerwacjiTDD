package com.example.systemrezerwacji.infrastructure.notification_mode;

import com.example.systemrezerwacji.domain.offer_module.Offer;
import com.example.systemrezerwacji.domain.offer_module.OfferFacade;
import com.example.systemrezerwacji.domain.reservation_module.ReservationFacade;
import com.example.systemrezerwacji.domain.reservation_module.dto.ReservationToTomorrow;
import com.example.systemrezerwacji.domain.salon_module.Salon;
import com.example.systemrezerwacji.domain.salon_module.SalonFacade;
import com.example.systemrezerwacji.domain.salon_module.dto.SalonWithIdDto;
import com.example.systemrezerwacji.domain.user_module.UserFacade;
import com.example.systemrezerwacji.domain.user_module.dto.UserRegisterDto;
import com.example.systemrezerwacji.infrastructure.notification_mode.response.NotificationFacadeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class NotificationFacade {
    private final EmailService emailService;
    private final ReservationFacade reservationFacade;
    private final SalonFacade salonFacade;
    private final UserFacade userFacade;
    private final OfferFacade offerFacade;

    public NotificationFacadeResponse sendAnEmailWhenClientHasAccount(String to, String offerName, LocalDateTime time) {
        Boolean isSuccess = emailService.sendHtmlEmail(to, offerName, time);
        return new NotificationFacadeResponse(isSuccess);
    }

    public NotificationFacadeResponse sendAnEmailWhenClientDoNotHasAccount(String to, String offerName, String password,LocalDateTime time) {
        Boolean isSuccess = emailService.sendHtmlEmailWithPassword(to, offerName, password,time);
        return new NotificationFacadeResponse(isSuccess);
    }

    public List<NotificationFacadeResponse> sendRemind() {
        List<ReservationToTomorrow> allReservations = reservationFacade.getAllReservationToTomorrow();

        return allReservations.stream()
                .map(reservation -> {
                    Optional<SalonWithIdDto> salonOpt = salonFacade.getSalonById(reservation.salonId());
                    Optional<UserRegisterDto> userOpt = userFacade.getUserByid(reservation.userId());
                    Offer offer = offerFacade.getOffer(reservation.offerId());

                    if (salonOpt.isPresent() && userOpt.isPresent()) {
                        SalonWithIdDto salon = salonOpt.get();
                        UserRegisterDto user = userOpt.get();

                        return remindUserToReservation(
                                user.email(),
                                offer.getName(),
                                salon,
                                reservation.reservationDataTime()
                        );
                    } else {
                        return new NotificationFacadeResponse(false);
                    }
                })
                .toList();
    }




    private NotificationFacadeResponse remindUserToReservation(String to, String offerName, SalonWithIdDto salon, LocalDateTime time ) {
        Boolean isSuccess = emailService.sendHtmlEmailToRemindAboutReservation(to,offerName,salon.salonName(),salon.city(),
                salon.street(),salon.number(),time);
        return new NotificationFacadeResponse(isSuccess);
    }

}
