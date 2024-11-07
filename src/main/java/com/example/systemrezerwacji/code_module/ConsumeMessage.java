package com.example.systemrezerwacji.code_module;


public record ConsumeMessage(String message, boolean isSuccess) {
    static final String SUCCESS_MESSAGE = "success";

    static ConsumeMessage success() {
        return new ConsumeMessage(SUCCESS_MESSAGE, true);
    }

    static ConsumeMessage failure(String message) {
        return new ConsumeMessage(message, false);
    }
}
