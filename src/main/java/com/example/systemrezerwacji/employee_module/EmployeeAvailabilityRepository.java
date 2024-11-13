package com.example.systemrezerwacji.employee_module;

import com.example.systemrezerwacji.day_of_week.DayOfWeek;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface EmployeeAvailabilityRepository extends CrudRepository<EmployeeAvailability, Long> {
    Optional<EmployeeAvailability> findByEmployeeIdAndDayOfWeek(Long employee_id, java.time.DayOfWeek dayOfWeek);
}
