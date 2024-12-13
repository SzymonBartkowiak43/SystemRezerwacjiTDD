package com.example.systemrezerwacji.domain.code_module;

enum CodeError {

    CODE_NOT_FOUND("Code not found."),
    CODE_ALREADY_CONSUMED("Code is already consumed!");

    final String message;

    CodeError(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }
}
