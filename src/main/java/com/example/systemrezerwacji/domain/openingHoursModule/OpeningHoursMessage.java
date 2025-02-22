package com.example.systemrezerwacji.domain.openingHoursModule;

record OpeningHoursMessage(String message, boolean isSuccess) {
    static final String SUCCESS_MESSAGE = "success";

    static OpeningHoursMessage success() {
        return new OpeningHoursMessage(SUCCESS_MESSAGE, true);
    }

    static OpeningHoursMessage failure(String message) {
        return new OpeningHoursMessage(message, false);
    }
}
