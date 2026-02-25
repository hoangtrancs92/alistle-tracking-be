package alistle.com.identifyservice.application.service.impl;

import alistle.com.identifyservice.application.dto.request.CreateUserRequest;
import alistle.com.identifyservice.application.dto.request.LoginRequest;
import alistle.com.identifyservice.application.dto.response.AuthenticationResponse;
import alistle.com.identifyservice.application.dto.response.UserResponse;
import alistle.com.identifyservice.application.exception.AppException;
import alistle.com.identifyservice.application.exception.ErrorCode;
import alistle.com.identifyservice.application.mapper.UserMapper;
import alistle.com.identifyservice.application.service.UserAppService;
import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.service.UserDomainService;
import alistle.com.identifyservice.infrastructure.security.JwtUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAppServiceImpl implements UserAppService {
    UserDomainService userDomainService;
    UserMapper userMapper;
    JwtUtil jwtUtil;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        // Validate password confirmation
        if (!request.isPasswordMatching()) {
            throw new IllegalArgumentException("Password and confirm password do not match");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User createdUser = userDomainService.createUser(
                userMapper.toDomain(request)
        );

        return userMapper.toResponse(createdUser);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userDomainService.findUserByEmail(email);
        return userMapper.toResponse(user);
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userDomainService.findUserByEmail(request.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(request.getPassword()));

        boolean isValid = user.authenticate(request.getPassword(), passwordEncoder);
        if (!isValid) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthenticationResponse(token, userMapper.toResponse(user));
    }
}
