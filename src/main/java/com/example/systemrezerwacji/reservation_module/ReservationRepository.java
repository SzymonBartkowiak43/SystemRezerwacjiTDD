package com.example.systemrezerwacji.reservation_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAll();
}



