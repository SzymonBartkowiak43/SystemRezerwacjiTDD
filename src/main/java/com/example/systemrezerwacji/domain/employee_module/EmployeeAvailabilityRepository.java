package com.example.systemrezerwacji.domain.employee_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeAvailabilityRepository extends CrudRepository<EmployeeAvailability, Long> {
    Optional<EmployeeAvailability> findByEmployeeIdAndDayOfWeek(Long employee_id, java.time.DayOfWeek dayOfWeek);
}
