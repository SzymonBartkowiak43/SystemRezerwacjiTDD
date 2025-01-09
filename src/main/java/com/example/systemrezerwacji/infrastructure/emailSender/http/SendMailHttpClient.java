package com.example.systemrezerwacji.infrastructure.emailSender.http;

import com.example.systemrezerwacji.infrastructure.emailSender.http.dto.EmailRequestDto;
import com.example.systemrezerwacji.infrastructure.emailSender.http.dto.EmailRequestWithPasswordDto;
import com.example.systemrezerwacji.infrastructure.notificationMode.SendMail;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class SendMailHttpClient implements SendMail {
    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public Boolean sendEmail(String to, String offerName, LocalDateTime time, String company) {
        log.info("Started sending email");
        try {
            String urlForService = getUrlForService("/api/mail/send");
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();

            EmailRequestDto emailRequest = new EmailRequestDto(to, offerName, time, company);
            HttpEntity<EmailRequestDto> requestEntity = new HttpEntity<>(emailRequest, getDefaultHeaders());

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
            );

            log.info("Response from service: " + response.getBody());
            return response.getBody() != null && response.getBody();
        } catch (ResourceAccessException e) {
            log.error("Error while sending email: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean sendEmailWithPassword(String to, String offerName, LocalDateTime time, String company, String password) {
        log.info("Started sending email with password");
        try {
            String urlForService = getUrlForService("/api/mail/send-with-password");
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();

            EmailRequestWithPasswordDto emailRequest = new EmailRequestWithPasswordDto(to, offerName, time, company,password);
            HttpEntity<EmailRequestWithPasswordDto> requestEntity = new HttpEntity<>(emailRequest, getDefaultHeaders());

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
            );

            log.info("Response from service: " + response.getBody());
            return response.getBody() != null && response.getBody();
        } catch (ResourceAccessException e) {
            log.error("Error while sending email: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Boolean> sendRemind(String to, String reservationName, String salonName, String street, String number, String city, LocalDateTime time) {
        return null;
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
