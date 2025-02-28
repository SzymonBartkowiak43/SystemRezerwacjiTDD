package com.example.systemrezerwacji.domain.reservationModule.response;

import com.example.systemrezerwacji.domain.userModule.dto.UserCreatedWhenRegisteredDto;

public record NotificationResult(
        boolean success,
        UserCreatedWhenRegisteredDto userInfo
) {}
