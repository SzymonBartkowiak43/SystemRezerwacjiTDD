package com.example.systemrezerwacji.domain.salonModule.dto;

public record ImageDto(int id,
                       String name,
                       String imageUrl,
                       String imageId,
                       Long salonId) {
}
