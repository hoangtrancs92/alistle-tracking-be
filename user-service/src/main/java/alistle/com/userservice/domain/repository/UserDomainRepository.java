package alistle.com.userservice.domain.repository;

import alistle.com.userservice.domain.model.User;
import alistle.com.userservice.domain.model.UserEmail;

import java.util.Optional;
import java.util.UUID;

public interface UserDomainRepository {
    String test();
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(UserEmail email);
    boolean existsByEmail(UserEmail email);
}
