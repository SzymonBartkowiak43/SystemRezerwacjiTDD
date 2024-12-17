package com.example.systemrezerwacji.infrastructure.notification_mode;


import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public Boolean sendHtmlEmail(String to, String offerName, LocalDateTime time) {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, Object> templateParams = Map.of(
                "subject", "Potwierdzenie usługi",
                "offerName", offerName,
                "time", formattedTime
        );

        String body = createHtmlBody("emailTemplate", templateParams);
        return sendEmail(to, "Potwierdzenie rezerwacji", body);
    }

    public Boolean sendHtmlEmailWithPassword(String to, String offerName, String password, LocalDateTime time) {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, Object> templateParams = Map.of(
                "subject", "Potwierdzenie usługi",
                "offerName", offerName,
                "password", password,
                "time", formattedTime
        );

        String body = createHtmlBody("emailTemplateWithPassword", templateParams);
        return sendEmail(to, offerName, body);
    }

    public Boolean sendHtmlEmailToRemindAboutReservation(String to, String offerName, String salonName, String city,
                                                         String street, String number, LocalDateTime time) {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, Object> templateParams = Map.of(
                "subject", "Przypomnienie o rezerwacji",
                "offerName", offerName,
                "salonName", salonName,
                "city", city,
                "street", street,
                "number", number,
                "time", formattedTime
        );

        String body = createHtmlBody("emailTemplateReservationReminder", templateParams);

        return sendEmail(to, "Przypomnienie o rezerwacji", body);
    }


    private Boolean sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createHtmlBody(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        variables.forEach(context::setVariable);
        return templateEngine.process(templateName, context);
    }
}