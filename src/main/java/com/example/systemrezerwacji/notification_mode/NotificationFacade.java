package com.example.systemrezerwacji.notification_mode;

import com.example.systemrezerwacji.notification_mode.response.NotificationFacadeResponse;
import org.springframework.stereotype.Component;

@Component
public class NotificationFacade {

    private final EmailService emailService;


    public NotificationFacade(EmailService emailService) {
        this.emailService = emailService;
    }

    public NotificationFacadeResponse sendAnEmailWhenClientHasReservered(String to, String offerName) {
        emailService.sendHtmlEmail(to,offerName,"Dziekujemy za zakup :p");
        return new NotificationFacadeResponse(true);
    }

}
