package alistle.com.userservice.domain.service;

import alistle.com.userservice.domain.model.User;
import alistle.com.userservice.domain.model.UserEmail;
import alistle.com.userservice.domain.model.UserPassword;

public interface UserDomainService {
    String test();
    User createUser(UserEmail email, UserPassword password);
    User findUserById(java.util.UUID id);
    User findUserByEmail(UserEmail email);
}
