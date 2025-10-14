package io.lorddead.networksystem.service;

import java.util.Arrays;
import java.util.Optional;

public enum UserChoice {
    ADD_NETWORK(1, "Add Network"),
    EXIT (10, "Exit");

    private final int code;
    private final String description;

    UserChoice(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<UserChoice> valueOf(int code) {
        return Arrays.stream(UserChoice.values())
                .filter(action -> action.getCode() == code)
                .findAny();
    }
}
