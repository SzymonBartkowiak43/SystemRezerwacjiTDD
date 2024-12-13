package com.example.systemrezerwacji.domain.code_module.dto;

import java.time.LocalDateTime;

public record CodeDto(String code, boolean isConsumed, LocalDateTime dataGenerated, Long userId) {
}
