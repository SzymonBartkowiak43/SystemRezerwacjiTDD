package com.example.systemrezerwacji.domain.employee_module;

import com.example.systemrezerwacji.domain.employee_module.dto.AvailableTermDto;
import com.example.systemrezerwacji.domain.employee_module.dto.EmployeeAvailabilityDto;
import com.example.systemrezerwacji.domain.employee_module.exception.EmployeeDuplicateOfferException;
import com.example.systemrezerwacji.domain.offer_module.Offer;
import com.example.systemrezerwacji.domain.user_module.User;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class EmployeeService {
    public static final int MINUTES_IN_INTERVAL = 30;

    private final EmployeeRepository employeeRepository;
    private final EmployeeAvailabilityService employeeAvailabilityService;

    EmployeeService(EmployeeRepository employeeRepository, EmployeeAvailabilityService employeeAvailabilityService) {
        this.employeeRepository = employeeRepository;
        this.employeeAvailabilityService = employeeAvailabilityService;
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


    List<AvailableTermDto> findAvailability(Long employeeId, LocalDate date, LocalTime duration, List<AvailableTermDto> employeeBusyTermsList) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        EmployeeAvailability availability =  employeeAvailabilityService.findEmployeeAvability(employeeId, dayOfWeek);

        return generateAvailableTerms(availability.getStartTime(), availability.getEndTime(), employeeBusyTermsList);

    }

    Long getUserIdByEmployeeId(Long employeeId) {
        Optional<Employee> byId = employeeRepository.findById(employeeId);
        return byId.get().getUser().getId();
    }

    Employee addOfferToEmployee(Long employeeId, Offer offer) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        if(employee.getOffers().contains(offer)) {
            throw new EmployeeDuplicateOfferException();
        }

        employee.addOffer(offer);
        Employee save = employeeRepository.save(employee);
        return save;

    }

    Employee getEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }


    private List<AvailableTermDto> generateAvailableTerms(LocalTime start, LocalTime end,List<AvailableTermDto> employeeBusyTermsList ) {
        List<AvailableTermDto> termsList = new ArrayList<>();

        Duration interval = Duration.ofMinutes(MINUTES_IN_INTERVAL);

        LocalTime currentStart = start;
        while (currentStart.plus(interval).isBefore(end) || currentStart.plus(interval).equals(end)) {
            LocalTime currentEnd = currentStart.plus(interval);
            termsList.add(new AvailableTermDto(currentStart, currentEnd));
            currentStart = currentStart.plusMinutes(MINUTES_IN_INTERVAL );
        }


        return termsList.stream()
                .filter(term -> employeeBusyTermsList.stream()
                        .noneMatch(busyTerm ->
                                term.startServices().equals(busyTerm.startServices()) &&
                                        term.endServices().equals(busyTerm.endServices())
                        )
                ).toList();

    }
}
