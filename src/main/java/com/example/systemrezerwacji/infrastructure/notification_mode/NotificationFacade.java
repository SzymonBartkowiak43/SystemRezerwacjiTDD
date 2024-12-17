package com.example.systemrezerwacji.infrastructure.notification_mode;

import com.example.systemrezerwacji.infrastructure.notification_mode.response.NotificationFacadeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class NotificationFacade {
    private final EmailService emailService;


    public NotificationFacadeResponse sendAnEmailWhenClientHasAccount(String to, String offerName, LocalDateTime time) {
        Boolean isSuccess = emailService.sendHtmlEmail(to, offerName, time);
        return new NotificationFacadeResponse(isSuccess);
    }

    public NotificationFacadeResponse sendAnEmailWhenClientDoNotHasAccount(String to, String offerName, String password,LocalDateTime time) {
        Boolean isSuccess = emailService.sendHtmlEmailWithPassword(to, offerName, password,time);
        return new NotificationFacadeResponse(isSuccess);
    }
}