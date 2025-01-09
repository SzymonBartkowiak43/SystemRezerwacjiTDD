package com.example.systemrezerwacji.infrastructure.notificationMode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@AllArgsConstructor
class EmailService {

    private final SendMail sendMail;

    public Boolean sendHtmlEmail(String to, String offerName, LocalDateTime time, String companyName) {
        try {
            sendMail.sendEmail(to, offerName, time, companyName);
        } catch (Exception e) {
            System.err.println("Error while sending email: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean sendHtmlEmailWithPassword(String to, String offerName, String password, LocalDateTime time, String companyName) {
        try {
            sendMail.sendEmailWithPassword(to,offerName,time,companyName, password);
        } catch (Exception e) {
            System.err.println("Error while sending email: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean sendHtmlEmailToRemindAboutReservation(String to, String offerName, String salonName, String city, String street, String number, LocalDateTime time) {
        try {
            sendMail.sendRemind(to, offerName, salonName, street, number, city, time);
        } catch (Exception e) {
            System.err.println("Error while sending email: " + e.getMessage());
            return false;
        }
        return true;
    }

}