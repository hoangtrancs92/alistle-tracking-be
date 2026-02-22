package alistle.com.userservice.application.service.impl;

import alistle.com.userservice.application.dto.request.CreateUserRequest;
import alistle.com.userservice.application.dto.response.UserResponse;
import alistle.com.userservice.application.mapper.UserMapper;
import alistle.com.userservice.application.service.UserAppService;
import alistle.com.userservice.domain.model.User;
import alistle.com.userservice.domain.model.UserEmail;
import alistle.com.userservice.domain.model.UserPassword;
import alistle.com.userservice.domain.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String testApplication() {
        return "User Application Service is working";
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        // Validate password confirmation
        if (!request.isPasswordMatching()) {
            throw new IllegalArgumentException("Password and confirm password do not match");
        }

        UserEmail email = userMapper.toUserEmail(request.getEmail());
        UserPassword password = userMapper.toUserPassword(request.getPassword());

        User createdUser = userDomainService.createUser(email, password);

        return userMapper.toResponse(createdUser);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userDomainService.findUserById(id);
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        UserEmail userEmail = userMapper.toUserEmail(email);
        User user = userDomainService.findUserByEmail(userEmail);
        return userMapper.toResponse(user);
    }
}
