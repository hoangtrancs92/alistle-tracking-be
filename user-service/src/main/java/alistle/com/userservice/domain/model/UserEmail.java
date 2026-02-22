package alistle.com.userservice.domain.model;

import java.util.Objects;

public record UserEmail(String value) {
    public UserEmail {
        Objects.requireNonNull(value, "Email cannot be null");

        if (!value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
