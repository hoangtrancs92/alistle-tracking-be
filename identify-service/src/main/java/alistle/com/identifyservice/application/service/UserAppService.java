package alistle.com.identifyservice.application.service;

import alistle.com.identifyservice.application.dto.request.CreateUserRequest;
import alistle.com.identifyservice.application.dto.response.UserResponse;

public interface UserAppService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
}
