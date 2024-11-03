package com.example.systemrezerwacji.code_module;

import com.example.systemrezerwacji.user_module.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@Entity
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Boolean isActive;
    private LocalDateTime dataGenerated;
    private LocalDateTime dataConsumption;

    @OneToOne
    private User user;
}
