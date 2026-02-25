package alistle.com.identifyservice.application.service;

import alistle.com.identifyservice.application.dto.request.CreateUserRequest;
import alistle.com.identifyservice.application.dto.request.LoginRequest;
import alistle.com.identifyservice.application.dto.response.AuthenticationResponse;
import alistle.com.identifyservice.application.dto.response.UserResponse;

public interface UserAppService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserByEmail(String email);
    AuthenticationResponse login(LoginRequest request);
}
