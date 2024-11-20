package com.example.systemrezerwacji.employee_module;

import com.example.systemrezerwacji.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.employee_module.dto.EmployeeDto;
import com.example.systemrezerwacji.employee_module.dto.EmployeeFacadeResponseDto;
import com.example.systemrezerwacji.employee_module.dto.EmployeeToOfferDto;
import com.example.systemrezerwacji.offer_module.Offer;
import com.example.systemrezerwacji.offer_module.OfferFacade;
import com.example.systemrezerwacji.reservation_module.ReservationFacade;
import com.example.systemrezerwacji.reservation_module.dto.AvailableDatesReservationDto;
import com.example.systemrezerwacji.salon_module.Salon;
import com.example.systemrezerwacji.salon_module.dto.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.user_module.User;
import com.example.systemrezerwacji.user_module.UserFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EmployeeFacade {

    private final UserFacade userFacade;
    private final EmployeeService employeeService;
    private final OfferFacade offerFacade;
    private final ReservationFacade reservationFacade;

    public EmployeeFacade(UserFacade userFacade, EmployeeService employeeService, OfferFacade offerFacade, ReservationFacade reservationFacade) {
        this.userFacade = userFacade;
        this.employeeService = employeeService;
        this.offerFacade = offerFacade;
        this.reservationFacade = reservationFacade;
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

        Map<Long, String> userIdAndName = userFacade.getEmployeeNameById(employeesUserIdById);

         return employeesId.stream()
                .map(employeeId -> {
                    Long userId = employeeService.getUserIdByEmployeeId(employeeId);
                    String name = userIdAndName.get(userId);
                    return new EmployeeToOfferDto(employeeId, name);
                })
                .toList();
    }

    public List<AvailableTermDto> getAvailableHours(AvailableDatesReservationDto availableDate) {
        LocalTime duration = offerFacade.getDurationToOffer(availableDate.offerId());

        List<AvailableTermDto> employeeBusyTermsList = reservationFacade.getEmployeeBusyTerm(availableDate.employeeId(), availableDate.date());

        List<AvailableTermDto> termsDto = employeeService.findAvailability(
                availableDate.employeeId(), availableDate.date(),duration, employeeBusyTermsList);

        return termsDto;
    }

    public Employee getEmployee(Long id) {
        return employeeService.getEmployee(id);
    }

    @Transactional
    public EmployeeFacadeResponseDto addOfferToEmployee(Long employeeId, Long offerId) {
        Offer offer = offerFacade.getOffer(offerId);

        employeeService.addOfferToEmployee(employeeId, offer);

        return new EmployeeFacadeResponseDto("success", employeeId);
    }
}
