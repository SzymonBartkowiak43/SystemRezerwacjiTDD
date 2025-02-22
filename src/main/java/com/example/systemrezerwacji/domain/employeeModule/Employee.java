package com.example.systemrezerwacji.domain.employeeModule;


import com.example.systemrezerwacji.domain.offerModule.Offer;
import com.example.systemrezerwacji.domain.userModule.User;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeAvailability> availability = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "employee_offers",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    private List<Offer> offers = new ArrayList<>();

    public Employee() {

    }

    void setSalonAndUser(Salon salon, User user) {
        this.salon = salon;
        this.user = user;
    }

    void setAvailability(List<EmployeeAvailability> availability) {
        this.availability = availability;
    }

    void addOffer(Offer offer) {
        this.offers.add(offer);
    }
}
