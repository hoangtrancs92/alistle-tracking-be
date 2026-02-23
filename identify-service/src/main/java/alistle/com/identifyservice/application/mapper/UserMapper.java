package alistle.com.identifyservice.application.mapper;

import alistle.com.identifyservice.application.dto.response.UserResponse;
import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.model.UserEmail;
import alistle.com.identifyservice.domain.model.UserPassword;
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
