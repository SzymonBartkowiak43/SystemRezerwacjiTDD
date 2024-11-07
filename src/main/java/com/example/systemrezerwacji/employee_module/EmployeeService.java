package com.example.systemrezerwacji.employee_module;

import com.example.systemrezerwacji.employee_module.dto.EmployeeAvailabilityDto;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
class EmployeeService {
    private final EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }


    List<EmployeeAvailability> createAvailabilityList(List<EmployeeAvailabilityDto> availabilityDto, Employee employee) {
        return availabilityDto.stream()
                .map(dto -> {
                    EmployeeAvailability availability = new EmployeeAvailability();
                    availability.setEmployee(employee);
                    availability.setDayOfWeek(DayOfWeek.valueOf(dto.dayOfWeek()));
                    availability.setStartTime(dto.startTime());
                    availability.setEndTime(dto.endTime());
                    return availability;
                })
                .toList();
    }
}
