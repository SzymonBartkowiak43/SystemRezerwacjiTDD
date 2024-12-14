package com.example.systemrezerwacji.infrastructure.notification_mode;

import com.example.systemrezerwacji.infrastructure.notification_mode.response.NotificationFacadeResponse;
import org.springframework.stereotype.Component;

@Component
public class NotificationFacade {

    private static final String MESSAGE = "Thanks!!! :p";
    private final EmailService emailService;


    public NotificationFacade(EmailService emailService) {
        this.emailService = emailService;
    }

    public NotificationFacadeResponse sendAnEmailWhenClientHasAccount(String to, String offerName) {
        Boolean isSuccess = emailService.sendHtmlEmail(to, offerName, MESSAGE);
        return new NotificationFacadeResponse(isSuccess);
    }

    public NotificationFacadeResponse sendAnEmailWhenClientDoNotHasAccount(String to, String offerName, String password) {
        Boolean isSuccess = emailService.sendHtmlEmailWithPassword(to, offerName, MESSAGE, password);
        return new NotificationFacadeResponse(isSuccess);
    }
}
