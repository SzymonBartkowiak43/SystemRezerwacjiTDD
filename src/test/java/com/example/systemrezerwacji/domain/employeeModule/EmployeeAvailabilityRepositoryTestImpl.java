package com.example.systemrezerwacji.domain.employeeModule;

import java.time.DayOfWeek;
import java.util.Optional;

public class EmployeeAvailabilityRepositoryTestImpl implements EmployeeAvailabilityRepository{
    @Override
    public Optional<EmployeeAvailability> findByEmployeeIdAndDayOfWeek(Long employee_id, DayOfWeek dayOfWeek) {
        return Optional.empty();
    }

    @Override
    public <S extends EmployeeAvailability> S save(S entity) {
        return null;
    }

    @Override
    public <S extends EmployeeAvailability> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<EmployeeAvailability> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<EmployeeAvailability> findAll() {
        return null;
    }

    @Override
    public Iterable<EmployeeAvailability> findAllById(Iterable<Long> longs) {
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
    public void delete(EmployeeAvailability entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends EmployeeAvailability> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
