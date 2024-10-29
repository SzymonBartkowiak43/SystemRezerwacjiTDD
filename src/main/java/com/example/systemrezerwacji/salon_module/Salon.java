package com.example.systemrezerwacji.salon_module;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String salonName;
    private String category;
    private String city;
    private String zipCode;
    private String street;
    private String number;


    private Salon(SalonBuilder salonServiceBuilder) {
        this.salonName = salonServiceBuilder.name;
        this.category = salonServiceBuilder.category;
        this.city = salonServiceBuilder.city;
        this.zipCode = salonServiceBuilder.zipCode;
        this.street = salonServiceBuilder.street;
        this.number = salonServiceBuilder.number;
    }

    public Salon() {

    }


    static class SalonBuilder {
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