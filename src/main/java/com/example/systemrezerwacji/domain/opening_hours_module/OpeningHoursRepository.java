package com.example.systemrezerwacji.domain.opening_hours_module;


import com.example.systemrezerwacji.domain.salon_module.Salon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHoursRepository extends CrudRepository<OpeningHours, Long> {
    void deleteBySalon(Salon salon);
}
