package com.example.systemrezerwacji.domain.reservationModule.response;

import com.example.systemrezerwacji.domain.employeeModule.Employee;
import com.example.systemrezerwacji.domain.offerModule.Offer;
import com.example.systemrezerwacji.domain.salonModule.Salon;
import com.example.systemrezerwacji.domain.userModule.dto.UserCreatedWhenRegisteredDto;

public record ReservationEntities(
        Salon salon,
        Employee employee,
        Offer offer,
        UserCreatedWhenRegisteredDto userInfo
        ) {}
