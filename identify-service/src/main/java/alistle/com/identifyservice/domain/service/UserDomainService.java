package alistle.com.identifyservice.domain.service;

import alistle.com.identifyservice.domain.model.User;

public interface UserDomainService {
    User createUser(User user);
    User findUserByEmail(String email);
}
