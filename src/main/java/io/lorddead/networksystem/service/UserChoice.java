package io.lorddead.networksystem.service;

import java.util.Arrays;
import java.util.Optional;

public enum UserChoice {
    ADD_NETWORK(1, "Add Network"),
    DELETE_NETWORK(2, "Delete Network"),
    UPDATE_NETWORK(3, "Update Network"),
    ADD_DEVICE(4, "Add Device"),
    DELETE_DEVICE(5, "Delete Device"),
    UPDATE_DEVICE(6, "Update device"),
    ADD_CONNECTION(7, "Add connection"),
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
