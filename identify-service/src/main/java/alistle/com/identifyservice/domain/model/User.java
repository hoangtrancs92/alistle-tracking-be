package alistle.com.identifyservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private final Long id;
    private final UserEmail email;
    private final UserPassword password;

    public static User create(UserEmail email, UserPassword password) {
        return new User(null, email, password);
    }

    public static User fromExisting(Long id, UserEmail email, UserPassword password) {
        return new User(id, email, password);
    }
}
