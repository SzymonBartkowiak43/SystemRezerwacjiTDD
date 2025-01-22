package com.example.systemrezerwacji.domain.openingHoursModule;


import com.example.systemrezerwacji.domain.salonModule.Salon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHoursRepository extends CrudRepository<OpeningHours, Long> {
    void deleteBySalon(Salon salon);
}
