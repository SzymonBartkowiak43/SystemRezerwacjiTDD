package com.example.systemrezerwacji.employee_module;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
class EmployeeAvailabilityService {
    private final EmployeeAvailabilityRepository employeeAvailabilityRepository;

    EmployeeAvailabilityService(EmployeeAvailabilityRepository employeeAvailabilityRepository) {
        this.employeeAvailabilityRepository = employeeAvailabilityRepository;
    }


    Iterable<EmployeeAvailability> saveAvability(List<EmployeeAvailability> employeeAvailabilityList) {
        return employeeAvailabilityRepository.saveAll(employeeAvailabilityList);
    }
}
