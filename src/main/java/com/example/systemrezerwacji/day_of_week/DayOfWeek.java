package com.example.systemrezerwacji.day_of_week;

import lombok.Getter;

@Getter
public enum DayOfWeek {
    MONDAY("Poniedziałek"),
    TUESDAY("Wtorek"),
    WEDNESDAY("Środa"),
    THURSDAY("Czwartek"),
    FRIDAY("Piątek"),
    SATURDAY("Sobota"),
    SUNDAY("Niedziela");

    private final String polishName;

    DayOfWeek(String polishName) {
        this.polishName = polishName;
    }

}