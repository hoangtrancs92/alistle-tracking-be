package alistle.com.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class User {
    private final UUID id;
    private final UserEmail email;
    private final UserPassword password;

    public static User create(UserEmail email, UserPassword password) {
        return new User(UUID.randomUUID(), email, password);
    }

    public static User fromExisting(UUID id, UserEmail email, UserPassword password) {
        return new User(id, email, password);
    }
}
