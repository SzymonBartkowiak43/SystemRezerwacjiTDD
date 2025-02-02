package com.example.systemrezerwacji.domain.reservationModule;

import com.example.systemrezerwacji.domain.reservationModule.dto.ReservationToTomorrow;
import com.example.systemrezerwacji.domain.reservationModule.dto.UserReservationDataDto;
import com.example.systemrezerwacji.domain.reservationModule.dto.UserReservationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class MapperReservationDto {

    UserReservationDto mapToUserReservationDto(Reservation reservation) {
        return new UserReservationDto(
                reservation.getId(),
                reservation.getSalon().getId(),
                reservation.getEmployee().getId(),
                reservation.getUser().getId(),
                reservation.getOffer().getId(),
                reservation.getReservationDateTime()
        );
    }


    List<UserReservationDto> mapToUserReservationDtoList(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToUserReservationDto)
                .collect(Collectors.toList());
    }

    UserReservationDataDto mapToUserReservationDataDto(Reservation reservation) {
        return new UserReservationDataDto(
                reservation.getId(),
                reservation.getSalon().getSalonName(),
                reservation.getEmployee().getUser().getName(),
                reservation.getOffer().getName(),
                reservation.getReservationDateTime()
        );
    }

    List<UserReservationDataDto> mapToUserReservationDataDtoList(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToUserReservationDataDto)
                .collect(Collectors.toList());
    }

    ReservationToTomorrow mapToReservationToTomorrow(Reservation reservation) {
        return new ReservationToTomorrow(
                reservation.getSalon().getId(),
                reservation.getUser().getId(),
                reservation.getOffer().getId(),
                reservation.getReservationDateTime()
        );
    }

    List<ReservationToTomorrow> mapToReservationToTomorrowList(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToReservationToTomorrow)
                .toList();
    }
}