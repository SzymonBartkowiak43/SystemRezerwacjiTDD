package com.example.systemrezerwacji.employee_module;

import com.example.systemrezerwacji.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.employee_module.dto.EmployeeToOfferDto;
import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.salon_module.dto.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.user_module.User;
import com.example.systemrezerwacji.user_module.UserFacade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EmployeeFacade {

    private final UserFacade userFacade;
    private final EmployeeService employeeService;

    public EmployeeFacade(UserFacade userFacade, EmployeeService employeeService) {
        this.userFacade = userFacade;
        this.employeeService = employeeService;
    }

    public CreateEmployeeResponseDto addEmployeeToSalon(EmployeeDto employeeDto, Salon salon) {
        Optional<User> userOptional = userFacade.createEmployee(employeeDto);
        if (userOptional.isEmpty()) {
            return new CreateEmployeeResponseDto("User creation failed", "", "");
        }
        User user = userOptional.get();

        Employee employee = new Employee();
        employee.setSalonAndUser(salon,user);

        List<EmployeeAvailability> availabilityList = employeeService.createAvailabilityList(employeeDto.availability(), employee);
        employee.setAvailability(availabilityList);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        return new CreateEmployeeResponseDto("success", user.getEmail(), user.getPassword());
    }

    public List<EmployeeToOfferDto> getEmployeesToOffer(Long id) {
        List<Long> employeesId = employeeService.findEmployeesIdByOfferId(id);

        List<Long> employeesUserIdById = employeeService.findEmployeesUserIdById(employeesId);

        Map<Long, String> userIdAndName = userFacade.getEmployeeNameByid(employeesUserIdById);

         return employeesId.stream()
                .map(employeeId -> {
                    Long userId = employeeService.getUserIdByEmployeeId(employeeId);
                    String name = userIdAndName.get(userId);
                    return new EmployeeToOfferDto(employeeId, name);
                })
                .toList();
    }
}
