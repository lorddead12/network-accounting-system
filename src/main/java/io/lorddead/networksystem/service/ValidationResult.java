package io.lorddead.networksystem.service;

public class ValidationResult {

    private final int code;
    private final boolean valid;

    public ValidationResult(int code, boolean valid) {
        this.code = code;
        this.valid = valid;
    }

    public int getCode() {
        return code;
    }

    public boolean isValid() {
        return valid;
    }
}
