package com.example.systemrezerwacji.code_module;

import com.example.systemrezerwacji.user_module.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Boolean isConsumed;
    private LocalDateTime dataGenerated;
    private LocalDateTime dataConsumption;

    @OneToOne
    private User user;

    Code(String code) {
        this.code = code;
        this.isConsumed = false;
        this.dataGenerated = LocalDateTime.now();
    }
    public Code() {

    }

    Boolean setConsumed() {
        this.isConsumed = true;
        return true;
    }

    LocalDateTime setDataConsumption() {
        this.dataConsumption = LocalDateTime.now();
        return dataConsumption;
    }

    User setUser(User user) {
        this.user = user;
        return user;
    }
}
