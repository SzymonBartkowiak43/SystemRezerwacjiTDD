package com.example.systemrezerwacji.reservation_module;


import com.example.systemrezerwacji.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.reservation_module.dto.CreateReservationDto;
import com.example.systemrezerwacji.reservation_module.dto.ReservationValidationResult;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
class ReservationValidator {

    public static final String EMPLOYEE_IS_BUSY = "Employee is busy during the selected time.";
    public static final String SELECTED_TIME_IA_AVAILABLE = "The selected time slot is available.";
    private final ReservationService reservationService;

    ReservationValidator(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    ReservationValidationResult validate(CreateReservationDto reservationDto, LocalTime duration) {
        Long employeeId = reservationDto.employeeId();
        LocalDate dateOfReservation =  reservationDto.reservationDateTime().toLocalDate();
        LocalTime startTime = reservationDto.reservationDateTime().toLocalTime();
        LocalTime endTime = startTime.plusHours(duration.getHour()).plusMinutes(duration.getMinute());


        List<AvailableTermDto> employeeBusyTerms = reservationService.getEmployeeBusyTerms(employeeId ,dateOfReservation);

        boolean hasConflict = employeeBusyTerms.stream()
                .anyMatch(busyTerm -> {
                    LocalTime busyStart = busyTerm.startServices();
                    LocalTime busyEnd = busyStart.plusHours(duration.getHour())
                            .plusMinutes(duration.getMinute());
                    return startTime.isBefore(busyEnd) && endTime.isAfter(busyStart);
                });

        if (hasConflict) {
            return new ReservationValidationResult(false, EMPLOYEE_IS_BUSY);
        }

        return new ReservationValidationResult(true, SELECTED_TIME_IA_AVAILABLE);


    }

}
