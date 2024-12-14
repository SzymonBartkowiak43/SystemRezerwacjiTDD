package com.example.systemrezerwacji.domain.salon_module.dto;

public record SalonWithIdDto(String id,
                             String salonName,
                             String category,
                             String city,
                             String zipCode,
                             String street,
                             String number,
                             String userId) {
}
