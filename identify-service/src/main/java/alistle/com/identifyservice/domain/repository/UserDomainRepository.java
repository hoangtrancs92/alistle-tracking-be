package alistle.com.identifyservice.domain.repository;

import alistle.com.identifyservice.domain.model.User;

import java.util.Optional;

public interface UserDomainRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
