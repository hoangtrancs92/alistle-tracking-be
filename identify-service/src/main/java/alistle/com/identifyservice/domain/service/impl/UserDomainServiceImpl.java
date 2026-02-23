package alistle.com.identifyservice.domain.service.impl;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.model.UserEmail;
import alistle.com.identifyservice.domain.model.UserPassword;
import alistle.com.identifyservice.domain.repository.UserDomainRepository;
import alistle.com.identifyservice.domain.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Autowired
    private UserDomainRepository userDomainRepository;

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
    public User findUserById(Long id) {
        return userDomainRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    public User findUserByEmail(UserEmail email) {
        return userDomainRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email.value()));
    }
}
