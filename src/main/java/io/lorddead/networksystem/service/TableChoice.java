package io.lorddead.networksystem.service;

import java.util.Arrays;
import java.util.Optional;

public enum TableChoice {
    CONNECTION(1, "Connection"),
    DEVICE(2, "Device"),
    NETWORK(3, "Network"),
    EXIT(4, "Exit");

    private final int code;
    private final String description;

    TableChoice(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Optional<TableChoice> valueOf(int code) {
        return Arrays.stream(TableChoice.values())
                .filter(tableChoice -> tableChoice.getCode() == code)
                .findAny();
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
