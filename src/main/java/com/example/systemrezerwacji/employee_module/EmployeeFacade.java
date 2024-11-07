package com.example.systemrezerwacji.employee_module;

import com.example.systemrezerwacji.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.salon_module.dto.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.user_module.User;
import com.example.systemrezerwacji.user_module.UserFacade;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeFacade {

    private final UserFacade userFacade;
    private final EmployeeService employeeService;
    private final EmployeeAvailabilityService employeeAvailabilityService;

    public EmployeeFacade(UserFacade userFacade, EmployeeService employeeService, EmployeeAvailabilityService employeeAvailabilityService) {
        this.userFacade = userFacade;
        this.employeeService = employeeService;
        this.employeeAvailabilityService = employeeAvailabilityService;
    }

    public CreateEmployeeResponseDto addEmployeeToSalon(EmployeeDto employeeDto, Salon salon) {
        Optional<User> userOptional = userFacade.createEmployee(employeeDto);
        if (userOptional.isEmpty()) {
            return new CreateEmployeeResponseDto("User creation failed", "", "");
        }
        User user = userOptional.get();

        Employee employee = new Employee();
        employee.setSalonAndUser(salon,user);

        List<EmployeeAvailability> availabilityList = employeeDto.availability().stream()
                .map(dto -> {
                    EmployeeAvailability availability = new EmployeeAvailability();
                    availability.setEmployee(employee);
                    availability.setDayOfWeek(DayOfWeek.valueOf(dto.dayOfWeek()));
                    availability.setStartTime(dto.startTime());
                    availability.setEndTime(dto.endTime());
                    return availability;
                })
                .toList();

        employee.setAvailability(availabilityList);

        employeeService.saveEmployee(employee);

        return new CreateEmployeeResponseDto("success", user.getEmail(), user.getPassword());
    }
}
