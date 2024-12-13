package com.example.systemrezerwacji.domain.user_module.dto;

import com.example.systemrezerwacji.domain.user_module.User;

public record UserCreatedWhenRegisteredDto(User user, Boolean isNewUser) {
}
