package com.example.systemrezerwacji.domain.reservationModule;

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
}