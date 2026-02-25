package alistle.com.identifyservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Data
public class User {
    private final Long id;
    private final String email;
    private final String password;

    public static User create(String email, String password) {
        return new User(null, email, password);
    }

    public static User fromExisting(Long id, String email, String password) {
        return new User(id, email, password);
    }

    // Domain logic: Authentication
    public boolean authenticate(String plainPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(plainPassword, this.password);
    }
}
