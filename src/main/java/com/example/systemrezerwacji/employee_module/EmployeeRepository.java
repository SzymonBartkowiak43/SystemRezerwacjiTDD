package com.example.systemrezerwacji.employee_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByOffersId(Long offerId);
}
