package com.example.systemrezerwacji.domain.reservation_module;

import com.example.systemrezerwacji.domain.reservation_module.dto.UserReservationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class MapperReservationDto {

    UserReservationDto mapToUserReservationDto(Reservation reservation) {
        return new UserReservationDto(
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