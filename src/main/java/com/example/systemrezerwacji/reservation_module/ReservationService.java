package com.example.systemrezerwacji.reservation_module;

import com.example.systemrezerwacji.employee_module.dto.AvailableTermDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
class ReservationService {
    private final ReservationRepository reservationRepository;

    ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    List<AvailableTermDto> getEmployeeBusyTerms(Long employeeId, LocalDate date) {

        List<Reservation> allServicesOnSpecificDay = getReservations(employeeId, date);

        if(allServicesOnSpecificDay.isEmpty()) {
            return Collections.emptyList();
        }

        return getAvailableTermDto(allServicesOnSpecificDay);

    }

    private List<Reservation> getReservations(Long employeeId, LocalDate date) {
        return  reservationRepository.findAll().stream()
                .filter(employee -> employee.getEmployee().getId().equals(employeeId))
                .filter(data -> data.getReservationDateTime().toLocalDate().equals(date))
                .toList();

    }

    private  List<AvailableTermDto> getAvailableTermDto(List<Reservation> allServicesOnSpecificDay) {
        List<AvailableTermDto> list = allServicesOnSpecificDay.stream()
                .map(reservation -> {
                    LocalTime start = reservation.getReservationDateTime().toLocalTime();
                    LocalTime end = start.plusMinutes(reservation.getOffer().getDuration().getMinute());
                    return new AvailableTermDto(start, end);
                })
                .toList();
        return list;
    }


}
