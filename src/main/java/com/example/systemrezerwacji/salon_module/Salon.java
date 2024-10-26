package com.example.systemrezerwacji.salon_module;

public class Salon {
    private Long id;
    private final String name;
    private final String category;
    private final String city;
    private final String zipCode;
    private final String street;
    private final String number;


    private Salon(SalonBuilder salonServiceBuilder) {
        id = 1L;
        this.name = salonServiceBuilder.name;
        this.category = salonServiceBuilder.category;
        this.city = salonServiceBuilder.city;
        this.zipCode = salonServiceBuilder.zipCode;
        this.street = salonServiceBuilder.street;
        this.number = salonServiceBuilder.number;
    }


    static class SalonBuilder {
        SalonValidator salonValidator = new SalonValidator();
        private String name;
        private String category;
        private String city;
        private String zipCode;
        private String street;
        private String number;

        SalonBuilder addName(String name) {
            this.name = name;
            return this;
        }

        SalonBuilder addCategory(String category) {
            this.category = category;
            return this;
        }

        SalonBuilder addCity(String city) {
            this.city = city;
            return this;
        }

        SalonBuilder addZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        SalonBuilder addStreet(String street) {
            this.street = street;
            return this;
        }

        SalonBuilder addNumber(String number) {
            this.number = number;
            return this;
        }

        Salon build() {
            return new Salon(this);
        }

    }
}