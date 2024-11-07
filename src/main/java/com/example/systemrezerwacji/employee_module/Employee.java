package com.example.systemrezerwacji.employee_module;


import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.user_module.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    void setSalonAndUser(Salon salon, User user) {
        this.salon = salon;
        this.user = user;
    }

    void setAvailability(List<EmployeeAvailability> availability) {
        this.availability = availability;
    }
}
