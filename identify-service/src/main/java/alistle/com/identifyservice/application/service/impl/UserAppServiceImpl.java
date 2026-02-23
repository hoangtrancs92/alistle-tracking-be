package alistle.com.identifyservice.application.service.impl;

import alistle.com.identifyservice.application.dto.request.CreateUserRequest;
import alistle.com.identifyservice.application.dto.response.UserResponse;
import alistle.com.identifyservice.application.mapper.UserMapper;
import alistle.com.identifyservice.application.service.UserAppService;
import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.model.UserEmail;
import alistle.com.identifyservice.domain.model.UserPassword;
import alistle.com.identifyservice.domain.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        // Validate password confirmation
        if (!request.isPasswordMatching()) {
            throw new IllegalArgumentException("Password and confirm password do not match");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEmail email = userMapper.toUserEmail(request.getEmail());
        UserPassword password = userMapper.toUserPassword(passwordEncoder.encode(request.getPassword()));

        User createdUser = userDomainService.createUser(email, password);

        return userMapper.toResponse(createdUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
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
