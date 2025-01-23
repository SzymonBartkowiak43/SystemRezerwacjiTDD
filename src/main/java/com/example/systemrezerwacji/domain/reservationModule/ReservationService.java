package com.example.systemrezerwacji.domain.reservationModule;

import com.example.systemrezerwacji.domain.employeeModule.Employee;
import com.example.systemrezerwacji.domain.employeeModule.dto.AvailableTermDto;
import com.example.systemrezerwacji.domain.offerModule.Offer;
import com.example.systemrezerwacji.domain.reservationModule.dto.UserReservationDataDto;
import com.example.systemrezerwacji.domain.reservationModule.exception.ReservationDeleteException;
import com.example.systemrezerwacji.domain.userModule.User;
import com.example.systemrezerwacji.domain.reservationModule.dto.UserReservationDto;
import com.example.systemrezerwacji.domain.salonModule.Salon;
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


    public List<UserReservationDataDto> getReservationToCurrentUser(User user) {
        List<Reservation> allByUser = reservationRepository.findAllByUser(user);
        return mapperReservationDto.mapToUserReservationDataDtoList(allByUser);
    }

    public List<Reservation> getAllReservationToTomorrow() {
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime endOfTomorrow = startOfTomorrow.plusDays(1).minusSeconds(1);

        return reservationRepository.findAllByReservationDateTimeBetween(startOfTomorrow, endOfTomorrow);
    }

    public Boolean deleteReservation(Long reservationId, User userByEmail) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationDeleteException("Reservation not found"));

        if(userByEmail == reservation.getUser()) {
            reservationRepository.delete(reservation);
            return true;
        }
        throw new ReservationDeleteException("This is not a reservation for this user");
    }

    public UserReservationDto updateReservationDate(Long reservationId, User userByEmail, LocalDateTime reservationDataTime) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationDeleteException("Reservation not found"));

        if(userByEmail == reservation.getUser()) {
            reservation.setReservationDateTime(reservationDataTime);
            Reservation savedReservation = reservationRepository.save(reservation);
            return mapperReservationDto.mapToUserReservationDto(savedReservation);
        }
        throw new ReservationDeleteException("This is not a reservation for this user");
    }
}
