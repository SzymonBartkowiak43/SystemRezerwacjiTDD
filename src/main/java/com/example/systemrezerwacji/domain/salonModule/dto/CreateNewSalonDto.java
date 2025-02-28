package com.example.systemrezerwacji.domain.salonModule.dto;



public record CreateNewSalonDto(String salonName,
                                String category,
                                String city,
                                String zipCode,
                                String street,
                                String number,
                                String email,
                                String code){

}
