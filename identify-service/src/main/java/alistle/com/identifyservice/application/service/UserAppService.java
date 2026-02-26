package alistle.com.identifyservice.application.service;

import alistle.com.identifyservice.application.dto.request.CreateUserRequest;
import alistle.com.identifyservice.application.dto.request.IntrospectRequest;
import alistle.com.identifyservice.application.dto.request.LoginRequest;
import alistle.com.identifyservice.application.dto.request.RefreshRequest;
import alistle.com.identifyservice.application.dto.response.AuthenticationResponse;
import alistle.com.identifyservice.application.dto.response.IntrospectResponse;
import alistle.com.identifyservice.application.dto.response.RefreshResponse;
import alistle.com.identifyservice.application.dto.response.UserResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface UserAppService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserByEmail(String email);
    AuthenticationResponse login(LoginRequest request);
    IntrospectResponse introspect(IntrospectRequest request);
    RefreshResponse refreshToken(RefreshRequest refreshToken) throws ParseException, JOSEException;
}
