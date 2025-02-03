package com.example.systemrezerwacji.domain.employeeModule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeRepositoryTestImpl implements EmployeeRepository {

    private Map<Long, Employee> employeeDataBase = new ConcurrentHashMap<>();
    private Long id = 1L;

    @Override
    public List<Employee> findByOffersId(Long offerId) {
        return null;
    }

    @Override
    public List<Employee> findAllBySalonId(Long salonId) {
        return null;
    }

    @Override
    public <S extends Employee> S save(S entity) {
        employeeDataBase.put(id, entity);
        id++;
        return entity;
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Employee> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Iterable<Employee> findAllById(Iterable<Long> longs) {
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
    public void delete(Employee entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Employee> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
