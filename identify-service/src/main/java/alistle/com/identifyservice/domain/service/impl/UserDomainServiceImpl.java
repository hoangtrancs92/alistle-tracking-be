package alistle.com.identifyservice.domain.service.impl;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.repository.UserDomainRepository;
import alistle.com.identifyservice.domain.service.UserDomainService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserDomainServiceImpl implements UserDomainService {
    UserDomainRepository userDomainRepository;

    @Override
    public User createUser(User user) {
        // Create new user
        User newUser = User.create(user.getEmail(), user.getPassword());
        return userDomainRepository.save(newUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDomainRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
