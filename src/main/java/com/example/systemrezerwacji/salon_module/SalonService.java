package com.example.systemrezerwacji.salon_module;

import lombok.Getter;

@Getter
class SalonService {
    private final String name;
    private final String category;
    private final String city;
    private final String zipCode;
    private final String street;
    private final String number;



    private SalonService(SalonServiceBuilder salonServiceBuilder) {
        this.name = salonServiceBuilder.name;
        this.category = salonServiceBuilder.category;
        this.city = salonServiceBuilder.city;
        this.zipCode = salonServiceBuilder.zipCode;
        this.street = salonServiceBuilder.street;
        this.number = salonServiceBuilder.number;
    }




    static class SalonServiceBuilder {
        SalonValidator salonValidator = new SalonValidator();
        private String name;
        private String category;
        private String city;
        private String zipCode;
        private String street;
        private String number;

        public SalonServiceBuilder addName(String name) {
            this.name = name;
            return this;
        }

        public SalonServiceBuilder addCategory(String category) {
            this.category = category;
            return this;
        }

        public SalonServiceBuilder addCity(String city) {
            this.city = city;
            return this;
        }

        public SalonServiceBuilder addZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public SalonServiceBuilder addStreet(String street) {
            this.street = street;
            return this;
        }

        public SalonServiceBuilder addNumber(String number) {
            this.number = number;
            return this;
        }

        public SalonService build() {
            return new SalonService(this);
        }

    }
}
