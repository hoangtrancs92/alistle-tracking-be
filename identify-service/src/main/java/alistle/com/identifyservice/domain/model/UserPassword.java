package alistle.com.identifyservice.domain.model;

import java.util.Objects;

public record UserPassword(String value) {
    private static final int MIN_PASSWORD_LENGTH = 8;
    public UserPassword {
        Objects.requireNonNull(value, "Password cannot be null");
        if (value.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }
}
