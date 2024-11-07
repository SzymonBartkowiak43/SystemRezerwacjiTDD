package com.example.systemrezerwacji.employee_module;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class EmployeeAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
