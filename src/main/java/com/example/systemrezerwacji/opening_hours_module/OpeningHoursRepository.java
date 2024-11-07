package com.example.systemrezerwacji.opening_hours_module;


import com.example.systemrezerwacji.salon_module.Salon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OpeningHoursRepository extends CrudRepository<OpeningHours, Long> {
    void deleteBySalon(Salon salon);
}
