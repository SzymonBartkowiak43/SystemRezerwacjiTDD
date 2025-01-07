package com.example.systemrezerwacji.domain.userModule.response;

import jakarta.annotation.Nullable;

public record UserFacadeResponse(String message, @Nullable Long userId,@Nullable String name) {
}
