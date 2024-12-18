package com.example.systemrezerwacji.domain.reservation_module;

import com.example.systemrezerwacji.domain.employee_module.Employee;
import com.example.systemrezerwacji.domain.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.domain.offer_module.Offer;
import com.example.systemrezerwacji.domain.user_module.User;
import com.example.systemrezerwacji.domain.reservation_module.dto.UserReservationDto;
import com.example.systemrezerwacji.domain.salon_module.Salon;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MapperReservationDto mapperReservationDto;


    ReservationService(ReservationRepository reservationRepository, MapperReservationDto mapperReservationDto) {
        this.reservationRepository = reservationRepository;
        this.mapperReservationDto = mapperReservationDto;
    }

    List<AvailableTermDto> getEmployeeBusyTerms(Long employeeId, LocalDate date) {
        List<Reservation> allServicesOnSpecificDay = getReservations(employeeId, date);

        if (allServicesOnSpecificDay.isEmpty()) {
            return Collections.emptyList();
        }
        return getAvailableTermDto(allServicesOnSpecificDay);
    }

    void addNewReservation(Salon salon, Employee employee, User user, Offer offer, LocalDateTime reservationDateTime) {
        Reservation reservation = new Reservation(salon,employee,user,offer,reservationDateTime);

        reservationRepository.save(reservation);
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


    public List<UserReservationDto> getReservationToCurrentUser(User user) {
        List<Reservation> allByUser = reservationRepository.findAllByUser(user);
        return mapperReservationDto.mapToUserReservationDtoList(allByUser);
    }

    public List<Reservation> getAllReservationToTomorrow() {
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime endOfTomorrow = startOfTomorrow.plusDays(1).minusSeconds(1);

        return reservationRepository.findAllByReservationDateTimeBetween(startOfTomorrow, endOfTomorrow);
    }
}
