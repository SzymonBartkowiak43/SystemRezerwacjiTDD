package com.example.systemrezerwacji.salon_module;

class SalonService {

    void createNewSalon(String name,
                        String category,
                        String city,
                        String zipCode,
                        String street,
                        String number) {
        Salon salon = new Salon.SalonBuilder()
                .addName(name)
                .addCity(city)
                .addCategory(category)
                .addZipCode(zipCode)
                .addStreet(street)
                .addNumber(number)
                .build();
    }
}
