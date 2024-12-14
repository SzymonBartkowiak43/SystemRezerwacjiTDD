package com.example.systemrezerwacji.domain.salon_module.dto;


public record CreatedNewSalonDto(String salonName,
                                 String category,
                                 String city,
                                 String zipCode,
                                 String street,
                                 String number,
                                 String userId,
                                 String code){

}
