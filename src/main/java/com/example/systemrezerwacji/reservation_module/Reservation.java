package com.example.systemrezerwacji.reservation_module;

import com.example.systemrezerwacji.employee_module.Employee;
import com.example.systemrezerwacji.offer_module.Offer;
import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.user_module.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;

    @Getter
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @Getter
    private LocalDateTime reservationDateTime;

}