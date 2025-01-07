package com.example.systemrezerwacji.domain.employeeModule;

import com.example.systemrezerwacji.domain.employeeModule.dto.AvailableTermDto;
import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeDto;
import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeFacadeResponseDto;
import com.example.systemrezerwacji.domain.employeeModule.dto.EmployeeToOfferDto;
import com.example.systemrezerwacji.domain.offerModule.Offer;
import com.example.systemrezerwacji.domain.offerModule.OfferFacade;
import com.example.systemrezerwacji.domain.reservationModule.ReservationFacade;
import com.example.systemrezerwacji.domain.employeeModule.response.CreateEmployeeResponseDto;
import com.example.systemrezerwacji.domain.userModule.UserFacade;
import com.example.systemrezerwacji.domain.reservationModule.dto.AvailableDatesReservationDto;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.userModule.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class EmployeeFacade {

    private static final String USER_CREATION_FAILED = "User creation failed";
    private final UserFacade userFacade;
    private final OfferFacade offerFacade;
    private final ReservationFacade reservationFacade;
    private final EmployeeService employeeService;


    public CreateEmployeeResponseDto createEmployeeAndAddToSalon(EmployeeDto employeeDto, Salon salon) {
        Optional<User> userOptional = userFacade.createEmployee(employeeDto);
        if (userOptional.isEmpty()) {
            return CreateEmployeeResponseDto.builder()
                    .message(USER_CREATION_FAILED)
                    .build();
        }
        User user = userOptional.get();

        Employee employee = new Employee();
        employee.setSalonAndUser(salon,user);
        List<EmployeeAvailability> availabilityList = employeeService.createAvailabilityList(employeeDto.availability(), employee);
        employee.setAvailability(availabilityList);

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return CreateEmployeeResponseDto.builder()
                .message("success")
                .employeeEmail(user.getEmail())
                .employeePassword(user.getPassword())
                .build();
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

    @Transactional
    public EmployeeFacadeResponseDto addOfferToEmployee(Long employeeId, Long offerId) {
        Offer offer = offerFacade.getOffer(offerId);

        Employee employee = employeeService.addOfferToEmployee(employeeId, offer);

        return new EmployeeFacadeResponseDto("success", employee.getId());
    }

    public Employee getEmployee(Long id) {
        return employeeService.getEmployee(id);
    }
}
