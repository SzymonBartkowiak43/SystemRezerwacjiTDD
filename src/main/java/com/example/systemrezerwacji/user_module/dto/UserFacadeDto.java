package com.example.systemrezerwacji.user_module.dto;

import jakarta.annotation.Nullable;

public record UserFacadeDto(String message, @Nullable Long userId) {
}
