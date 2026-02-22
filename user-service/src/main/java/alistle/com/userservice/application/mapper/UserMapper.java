package alistle.com.userservice.application.mapper;

import alistle.com.userservice.application.dto.request.CreateUserRequest;
import alistle.com.userservice.application.dto.response.UserResponse;
import alistle.com.userservice.domain.model.User;
import alistle.com.userservice.domain.model.UserEmail;
import alistle.com.userservice.domain.model.UserPassword;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEmail toUserEmail(String email) {
        return new UserEmail(email);
    }

    public UserPassword toUserPassword(String password) {
        return new UserPassword(password);
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail().value());
    }
}
