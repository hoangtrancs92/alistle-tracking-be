package alistle.com.identifyservice.domain.repository;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.model.UserEmail;

import java.util.Optional;

public interface UserDomainRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(UserEmail email);
    boolean existsByEmail(UserEmail email);
}
