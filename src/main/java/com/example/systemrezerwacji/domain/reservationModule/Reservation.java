package com.example.systemrezerwacji.domain.reservationModule;

import com.example.systemrezerwacji.domain.offerModule.Offer;
import com.example.systemrezerwacji.domain.employeeModule.Employee;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.userModule.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;

    @Getter
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Getter
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