package com.example.systemrezerwacji.infrastructure.loginandregister.notification_mode;


import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public Boolean sendHtmlEmail(String to, String subject, String message) {
        return sendEmail(to, subject, createHtmlBody("emailTemplate", Map.of(
                "subject", subject,
                "message", message
        )));
    }

    public Boolean sendHtmlEmailWithPassword(String to, String subject, String message, String password) {
        return sendEmail(to, subject, createHtmlBody("emailTemplateWithPassword", Map.of(
                "subject", subject,
                "message", message,
                "password", password
        )));
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