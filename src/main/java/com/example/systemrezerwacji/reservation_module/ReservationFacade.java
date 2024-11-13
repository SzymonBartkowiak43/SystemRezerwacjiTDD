package com.example.systemrezerwacji.reservation_module;


import com.example.systemrezerwacji.employee_module.dto.AvailableTermDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationFacade {

    private final ReservationService reservationService;

    public ReservationFacade(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    public List<AvailableTermDto> getEmployeeBusyTerm(Long employeeId, LocalDate date) {
        return reservationService.getEmployeeBusyTerms(employeeId, date);
    }
}
