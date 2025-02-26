package com.example.systemrezerwacji.domain.reservationModule;

import com.example.systemrezerwacji.domain.userModule.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationRepositoryTestImpl implements ReservationRepository{

    private Map<Long, Reservation> reservationsDataBase = new ConcurrentHashMap<>();
    private Long id = 1L;

    @Override
    public <S extends Reservation> S save(S entity) {
        reservationsDataBase.put(id, entity);
        id++;
        return entity;
    }

    @Override
    public <S extends Reservation> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Reservation> findById(Long aLong) {
        return Optional.ofNullable(reservationsDataBase.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationsDataBase.values().stream().toList();
    }

    @Override
    public Iterable<Reservation> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Reservation entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Reservation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Reservation> findAllByUser(User user) {
        return null;
    }

    @Override
    public List<Reservation> findAllByReservationDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<Reservation> findAllBySalonId(Long salonId) {
        return null;
    }
}
