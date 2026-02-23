package alistle.com.identifyservice.infrastructure.persistence.mapper;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.model.UserEmail;
import alistle.com.identifyservice.domain.model.UserPassword;
import alistle.com.identifyservice.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getEmail().value(),
                user.getPassword().value()
        );
    }

    public User toDomain(UserEntity entity) {
        return User.fromExisting(
                entity.getId(),
                new UserEmail(entity.getEmail()),
                new UserPassword(entity.getPassword())
        );
    }
}
