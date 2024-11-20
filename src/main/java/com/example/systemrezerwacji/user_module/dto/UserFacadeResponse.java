package com.example.systemrezerwacji.user_module.dto;

import jakarta.annotation.Nullable;

public record UserFacadeResponse(String message, @Nullable Long userId) {
}
