package alistle.com.userservice.domain.service.impl;

import alistle.com.userservice.domain.model.User;
import alistle.com.userservice.domain.model.UserEmail;
import alistle.com.userservice.domain.model.UserPassword;
import alistle.com.userservice.domain.repository.UserDomainRepository;
import alistle.com.userservice.domain.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Autowired
    private UserDomainRepository userDomainRepository;

    @Override
    public String test() {
        return userDomainRepository.test();
    }

    @Override
    public User createUser(UserEmail email, UserPassword password) {
        // Business rule: Check if user with email already exists
        if (userDomainRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Create new user
        User newUser = User.create(email, password);
        return userDomainRepository.save(newUser);
    }

    @Override
    public User findUserById(UUID id) {
        return userDomainRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    public User findUserByEmail(UserEmail email) {
        return userDomainRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email.value()));
    }
}
