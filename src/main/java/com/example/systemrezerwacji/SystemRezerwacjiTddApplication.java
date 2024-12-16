package com.example.systemrezerwacji;

import com.example.systemrezerwacji.infrastructure.security.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(value = JwtConfigurationProperties.class)
public class SystemRezerwacjiTddApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemRezerwacjiTddApplication.class, args);
    }


}
