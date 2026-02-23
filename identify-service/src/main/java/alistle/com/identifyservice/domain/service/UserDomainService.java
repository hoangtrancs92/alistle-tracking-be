package alistle.com.identifyservice.domain.service;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.model.UserEmail;
import alistle.com.identifyservice.domain.model.UserPassword;

public interface UserDomainService {
    User createUser(UserEmail email, UserPassword password);
    User findUserById(Long id);
    User findUserByEmail(UserEmail email);
}
