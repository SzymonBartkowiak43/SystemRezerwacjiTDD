package com.example.systemrezerwacji.employee_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EmployeeAvailabilityRepository extends CrudRepository<EmployeeAvailability, Long> {
}
