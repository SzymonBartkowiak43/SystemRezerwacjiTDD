package com.example.systemrezerwacji.offer_module;

import com.example.systemrezerwacji.salon_module.Salon;
import jakarta.persistence.*;
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
}
