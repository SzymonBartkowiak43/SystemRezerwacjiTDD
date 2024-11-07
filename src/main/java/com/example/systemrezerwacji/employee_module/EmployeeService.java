package com.example.systemrezerwacji.employee_module;

import com.example.systemrezerwacji.employee_module.dto.EmployeeAvailabilityDto;
import com.example.systemrezerwacji.user_module.User;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
class EmployeeService {
    private final EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
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

    List<Long> findEmployeesIdByOfferId(Long offerId) {
        List<Employee> offersId = employeeRepository.findByOffersId(offerId);

        return offersId.stream()
                .map(Employee::getId)
                .toList();
    }

    List<Long> findEmployeesUserIdById(List<Long> employeesId) {
        List<Employee> allById = (List<Employee>) employeeRepository.findAllById(employeesId);

        return allById.stream()
                .map(Employee::getUser)
                .map(User::getId)
                .toList();
    }

    Long getUserIdByEmployeeId(Long employeeId) {
        Optional<Employee> byId = employeeRepository.findById(employeeId);
        return byId.get().getUser().getId();
    }








    void addServiceToEmployee(Long employeeId, List<Long> servicesId ) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }


}
