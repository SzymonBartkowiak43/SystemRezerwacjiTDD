package com.example.systemrezerwacji.domain.reservationModule;

import com.example.systemrezerwacji.domain.userModule.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAll();
    List<Reservation> findAllByUser(User user);
    List<Reservation> findAllByReservationDateTimeBetween(LocalDateTime start, LocalDateTime end);
}



