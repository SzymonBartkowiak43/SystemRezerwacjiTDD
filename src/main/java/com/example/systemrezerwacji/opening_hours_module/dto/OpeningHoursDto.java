package com.example.systemrezerwacji.opening_hours_module.dto;


import java.time.LocalTime;

public record OpeningHoursDto(Long salonId,String dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
}
