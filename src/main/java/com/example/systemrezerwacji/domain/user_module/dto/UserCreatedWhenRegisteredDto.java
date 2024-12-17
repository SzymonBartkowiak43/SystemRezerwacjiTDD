package com.example.systemrezerwacji.domain.user_module.dto;

import com.example.systemrezerwacji.domain.user_module.User;
import jakarta.annotation.Nullable;

public record UserCreatedWhenRegisteredDto(User user, Boolean isNewUser,@Nullable String unHashedPassword) {
}
