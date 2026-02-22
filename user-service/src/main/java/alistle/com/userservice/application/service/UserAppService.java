package alistle.com.userservice.application.service;

import alistle.com.userservice.application.dto.request.CreateUserRequest;
import alistle.com.userservice.application.dto.response.UserResponse;

import java.util.UUID;

public interface UserAppService {
    String testApplication();
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserById(UUID id);
    UserResponse getUserByEmail(String email);
}
