package com.example.systemrezerwacji.domain.reservation_module;

import com.example.systemrezerwacji.domain.offer_module.Offer;
import com.example.systemrezerwacji.domain.employee_module.Employee;
import com.example.systemrezerwacji.domain.salon_module.Salon;
import com.example.systemrezerwacji.domain.user_module.User;
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

    Reservation(Salon salon, Employee employee, User user, Offer offer, LocalDateTime reservationDateTime) {
        this.salon = salon;
        this.employee = employee;
        this.user = user;
        this.offer = offer;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation() {
    }

}