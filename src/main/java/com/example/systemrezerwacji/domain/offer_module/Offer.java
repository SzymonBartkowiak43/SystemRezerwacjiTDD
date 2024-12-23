package com.example.systemrezerwacji.domain.offer_module;

import com.example.systemrezerwacji.domain.salon_module.Salon;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalTime duration;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    public Offer() {

    }

    Offer(String name, String description, BigDecimal price, LocalTime duration, Salon salon) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.salon = salon;
    }
}
