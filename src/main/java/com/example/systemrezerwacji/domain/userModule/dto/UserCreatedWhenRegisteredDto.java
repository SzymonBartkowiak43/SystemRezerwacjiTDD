package com.example.systemrezerwacji.domain.userModule.dto;

import com.example.systemrezerwacji.domain.userModule.User;
import jakarta.annotation.Nullable;

public record UserCreatedWhenRegisteredDto(User user, Boolean isNewUser,@Nullable String unHashedPassword) {
}
